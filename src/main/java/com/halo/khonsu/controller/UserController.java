package com.halo.khonsu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.halo.khonsu.entity.User;
import com.halo.khonsu.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chen
 * @since 2022-04-20
 */
@RestController
@RequestMapping("/user")
public class UserController {


   @Resource
   private IUserService userService;

   @PostMapping
   public boolean save(@RequestBody User user) {
        //新增或者更新
        return userService.saveOrUpdate(user);
        }
   @DeleteMapping("/{id}")
   public boolean delete(@PathVariable Integer id) {

        return userService.removeById(id);
        }

   @PostMapping("/del/batch")
   public boolean deletebatch(@RequestBody List<Integer> ids) {

        return userService.removeByIds(ids);
        }
   @GetMapping
   public List<User> findAll() {
        return userService.list();
        }
   @GetMapping("/{id}")
   public User findOne(@PathVariable Integer id) {
        return userService.getById(id);
        }
   @GetMapping("/page")
   public Page<User> findPage(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize,
                              @RequestParam(defaultValue = "") String username,
                              @RequestParam(defaultValue = "") String email,
                              @RequestParam(defaultValue = "") String address) {
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
      return userService.page(new Page<>(pageNum, pageSize),queryWrapper);
        }



        }

