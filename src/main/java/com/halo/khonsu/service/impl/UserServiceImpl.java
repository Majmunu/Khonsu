package com.halo.khonsu.service.impl;

import com.halo.khonsu.entity.User;
import com.halo.khonsu.mapper.UserMapper;
import com.halo.khonsu.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
