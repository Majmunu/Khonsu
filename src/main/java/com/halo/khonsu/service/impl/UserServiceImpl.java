package com.halo.khonsu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.khonsu.common.Constants;
import com.halo.khonsu.common.ValidationEnum;
import com.halo.khonsu.controller.dto.UserDTO;
import com.halo.khonsu.controller.dto.UserPasswordDTO;
import com.halo.khonsu.entity.Menu;
import com.halo.khonsu.entity.User;
import com.halo.khonsu.entity.Validation;
import com.halo.khonsu.exception.ServiceException;
import com.halo.khonsu.mapper.RoleMapper;
import com.halo.khonsu.mapper.RoleMenuMapper;
import com.halo.khonsu.mapper.UserMapper;
import com.halo.khonsu.service.IMenuService;
import com.halo.khonsu.service.IUserService;
import com.halo.khonsu.service.IValidationService;
import com.halo.khonsu.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
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

@Value("${spring.mail.username}")
private String from;
    @Autowired
    JavaMailSender javaMailSender;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    @Resource
    private IValidationService validationService;
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


    @Resource
    private UserMapper userMapper;
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        int update = userMapper.updatePassword(userPasswordDTO);
        if (update < 1) {
            throw new ServiceException(Constants.CODE_600, "密码错误");
        }
    }
     // 邮箱验证
    @Override
    public UserDTO loginEmail(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String code = userDTO.getCode();

        // 先查询 邮箱验证的表，看看之前有没有发送过  邮箱code，如果不存在，就重新获取
        QueryWrapper<Validation> validationQueryWrapper = new QueryWrapper<>();
        validationQueryWrapper.eq("email", email);
        validationQueryWrapper.eq("code", code);
        validationQueryWrapper.ge("time", new Date());  // 查询数据库没过期的code, where time >= new Date()
        Validation one = validationService.getOne(validationQueryWrapper);
        if (one == null) {
            throw new ServiceException("-1", "验证码过期，请重新获取");
        }

        // 如果验证通过了，就查询要不过户的信息
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email", email);  //根据email查询用户信息
        User user = getOne(userQueryWrapper);

        if (user == null) {
            throw new ServiceException("-1", "未找到用户");
        }

        BeanUtil.copyProperties(user, userDTO, true);


        // 设置token
        String token = TokenUtils.genToken(user.getId().toString(), user.getPassword());
        userDTO.setToken(token);

        String role = user.getRole();
        // 设置用户的菜单列表
        List<Menu> roleMenus = getRoleMenus(role);
        userDTO.setMenus(roleMenus);
        return userDTO;
    }
    // 发送邮箱验证码
    @Override
    public void sendEmailCode(String email,Integer type) throws MessagingException {
        Date now = new Date();
        // 先查询同类型code
        QueryWrapper<Validation> validationQueryWrapper = new QueryWrapper<>();
        validationQueryWrapper.eq("email", email);
        validationQueryWrapper.eq("type", type);
        validationQueryWrapper.ge("time", now);  // 查询数据库没过期的code
        Validation validation = validationService.getOne(validationQueryWrapper);
        if (validation != null) {
            throw new ServiceException("-1", "当前您的验证码仍然有效，请不要重复发送");
        }




        String code=RandomUtil.randomNumbers(4);
        if (ValidationEnum.LOGIN.getCode().equals(type)) {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message);
            helper.setFrom(from);  // 发送人
            helper.setTo(email);
            helper.setSentDate(now);  // 富文本
            helper.setSubject("【HALO🤪】登录邮箱验证");
            String context="<b>尊敬的用户：</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好，您本次登录的验证码是："+
                    "<a href='"+""+"' >"  + code + "</a><br>"
                    +"有效期15分钟。请妥善保管，切勿泄露</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果该修改邮箱邮件不是由你主动从酷安请求发出，请忽略！";
            helper.setText(context,true);
            javaMailSender.send(message);
        }else if(ValidationEnum.FORGET_PASS.getCode().equals(type)){
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message);
            helper.setSubject("【HALO🎉】找回密码邮箱验证");
            helper.setFrom(from);  // 发送人
            helper.setTo(email);
            helper.setSentDate(now);  // 富文本
            String context="<b>尊敬的用户：</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好，您本次重置密码的验证码是："+
                    "<a href='\"+\"\"+\"' >"  + code + "</a><br>"
                    +"有效期15分钟。请妥善保管，切勿泄露";
            helper.setText(context,true);
            javaMailSender.send(message);
        }




        //发送成功后，把验证码存入到数据库
        //
        validationService.saveCode(email, code, type, DateUtil.offsetMinute(now,15));
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
