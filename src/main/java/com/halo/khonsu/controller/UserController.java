package com.halo.khonsu.controller;

import com.halo.khonsu.entity.User;
import com.halo.khonsu.mapper.UserMapper;
import com.halo.khonsu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;
    @PostMapping
    public  Integer save(@RequestBody User user){
        //新增或者更新
        return userService.save(user);
    }
//查询所有数据
    @GetMapping
    public List<User> index(){

        return userMapper.findAll();
    }
//删除
    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id){
        return userMapper.deleteByid(id);
    }
}
