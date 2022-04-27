package com.halo.khonsu.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.halo.khonsu.entity.Menu;
import com.halo.khonsu.mapper.MenuMapper;
import com.halo.khonsu.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chen
 * @since 2022-04-26
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> findMenus(String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();

        if(StrUtil.isNotBlank(name)){
            queryWrapper.like("name",name);
        }



        List<Menu> list= list(queryWrapper);
        //找出pid为null的一级菜单
        List<Menu> parentNodes= list.stream().filter(menu -> menu.getPid()==null).collect(Collectors.toList());
        //找出一级菜单子菜单
        for (Menu menu : parentNodes) {
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));

        }
        return parentNodes;
    }
}
