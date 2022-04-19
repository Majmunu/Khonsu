package com.halo.khonsu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.halo.khonsu.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
//@Mapper
public interface UserMapper extends BaseMapper<User> {
   /* @Select("SELECT * FROM sys_user")
   List<User> findAll();
@Insert("INSERT INTO sys_user(username,password,nickname,email,phone,address) VALUES (#{username},#{password},#{nickname},#{email},#{phone},#{address})")
    int insert(User user);

    int update(User user);
@Delete("delete from sys_user where id=#{id}")
    Integer deleteByid(@Param("id") Integer id);

@Select("select * from sys_user where username like concat('%',#{username},'%') limit #{pageNum},#{pageSize}")
   List<User> selectPage(Integer pageNum, Integer pageSize,String username);
@Select("select count(*) from sys_user")
   *//* Integer selectTotal();*//*

    Integer selectTotal(String username);*/
}
