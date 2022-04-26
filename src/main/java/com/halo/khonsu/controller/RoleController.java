package com.halo.khonsu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.halo.khonsu.common.Result;
import com.halo.khonsu.entity.Role;
import com.halo.khonsu.service.IRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chen
 * @since 2022-04-26
 */
@RestController
@RequestMapping("/role")
public class RoleController {


   @Resource
   private IRoleService roleService;

// 新增或者更新
@PostMapping
public Result save(@RequestBody Role role) {
   roleService.saveOrUpdate(role);
        return Result.success();
        }

@DeleteMapping("/{id}")
public Result delete(@PathVariable Integer id) {
   roleService.removeById(id);
        return Result.success();
        }

@PostMapping("/del/batch")
public Result deleteBatch(@RequestBody List<Integer> ids) {
   roleService.removeByIds(ids);
        return Result.success();
        }

@GetMapping
public Result findAll() {
        return Result.success(roleService.list());
        }

@GetMapping("/{id}")
public Result findOne(@PathVariable Integer id) {
        return Result.success(roleService.getById(id));
        }

@GetMapping("/page")
public Result findPage(@RequestParam String name,
                       @RequestParam Integer pageNum,
                       @RequestParam Integer pageSize) {
    QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
    queryWrapper.like("name",name);
    queryWrapper.orderByDesc("id");

    return Result.success(roleService.page(new Page<>(pageNum, pageSize), queryWrapper));
}

}

