package com.halo.khonsu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.halo.khonsu.controller.dto.UserDTO;
import com.halo.khonsu.controller.dto.UserPasswordDTO;
import com.halo.khonsu.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chen
 * @since 2022-04-20
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User  register(UserDTO userDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);
}
