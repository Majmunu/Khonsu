package com.halo.khonsu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.halo.khonsu.controller.dto.UserPasswordDTO;
import com.halo.khonsu.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2022-04-20
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Update("update sys_user set password = #{newPassword} where username = #{username} and password = #{password}")
    int updatePassword(UserPasswordDTO userPasswordDTO);
}
