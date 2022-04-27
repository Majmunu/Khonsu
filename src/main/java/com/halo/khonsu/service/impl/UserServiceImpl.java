package com.halo.khonsu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.khonsu.common.Constants;
import com.halo.khonsu.controller.dto.UserDTO;
import com.halo.khonsu.entity.Menu;
import com.halo.khonsu.entity.User;
import com.halo.khonsu.exception.ServiceException;
import com.halo.khonsu.mapper.RoleMapper;
import com.halo.khonsu.mapper.RoleMenuMapper;
import com.halo.khonsu.mapper.UserMapper;
import com.halo.khonsu.service.IMenuService;
import com.halo.khonsu.service.IUserService;
import com.halo.khonsu.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chen
 * @since 2022-04-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
private static final Log LOG= Log.get();
@Resource
private RoleMapper roleMapper;

@Resource
private RoleMenuMapper roleMenuMapper;

@Resource
private IMenuService menuService;
    @Override
    public UserDTO login(UserDTO userDTO) {
        User one=getUserInfo(userDTO);
        if (one!=null) {
            BeanUtil.copyProperties(one,userDTO,true);
            //设置Token
            String token=TokenUtils.genToken(one.getId().toString(),one.getPassword());
            userDTO.setToken(token);

            String role = one.getRole();

             //设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
            return userDTO;
        } else  {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }


    }

    @Override
    public User register(UserDTO userDTO) {
        User one=getUserInfo(userDTO);
        if(one==null){
            one=new User();
            BeanUtil.copyProperties(userDTO,one,true);
            save(one); //把copy之后的用户对象存到数据库
        }else {
            throw new ServiceException(Constants.CODE_600,"用户已存在");
        }
        return null;
    }
    private User getUserInfo(UserDTO userDTO){
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try{
            one=getOne(queryWrapper); //从数据库查询用户信息


        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return one;
    }

    /**
     *  获取当前角色的菜单列表
     * @param roleFlag
     * @return
     */
    private List<Menu>  getRoleMenus(String roleFlag){
        Integer roleId = roleMapper.selectByFlag(roleFlag);
// 当前角色的所有菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
        //查出系统所有的菜单
        List<Menu> menus = menuService.findMenus("");
        //new一个筛选完成的list
        List<Menu> roleMenus=new ArrayList<>();
        //筛选当前用户角色的菜单

        for (Menu menu : menus) {
            if(menuIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            // removeIf()  移除 children 里面不在 menuIds集合中的 元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return  roleMenus;
    }
}
