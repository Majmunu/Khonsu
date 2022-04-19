package com.halo.khonsu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.halo.khonsu.entity.User;
import com.halo.khonsu.mapper.UserMapper;
import com.halo.khonsu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.QueryExp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping
    public boolean save(@RequestBody User user) {
        //新增或者更新
        return userService.saveUser(user);
    }

    //查询所有数据
    @GetMapping
    public List<User> findAll() {
       /* List<User> all = userMapper.findAll();*/
        return userService.list();
    }

    //删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {

        return userService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean deletebatch(@RequestBody List<Integer> ids) {

        return userService.removeByIds(ids);
    }

    //分页查询接口
    //接口路径/user/page
    //@RequestParam接收 pageNumber=1或者pageSize=10这样的数据
    //limit第一个参数 (pageNum-1)*pageSize
    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String address) {
        IPage<User> page=new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if (!"".equals(username)) {
            queryWrapper.like("username", username);
        }
        if (!"".equals(email)) {
            queryWrapper.like("email", email);
        }
        if (!"".equals(address)) {
            queryWrapper.like("address", address);
        }
        queryWrapper.orderByDesc("id");
        return userService.page(page,queryWrapper);
    }
    /*GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String username) {
        IPage <User> page =new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper= new QueryWrapper<>();
        return userService.page(page,queryWrapper);}*/




}
