package com.halo.khonsu.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.halo.khonsu.common.Result;
import com.halo.khonsu.entity.Course;
import com.halo.khonsu.service.ICourseService;
import com.halo.khonsu.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chen
 * @since 2022-05-11
 */
@RestController
@RequestMapping("/course")
public class CourseController {


    @Resource
    private ICourseService courseService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Course course) {
        if(course.getId()==null){
            course.setTime(DateUtil.now());
            course.setUser(TokenUtils.getCurrentUser().getNickname());
           course.setAvatarUrl(TokenUtils.getCurrentUser().getAvatarUrl());
        }
        courseService.saveOrUpdate(course);

        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        courseService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        courseService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(courseService.list());
    }


    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(courseService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam(required = false) Integer type,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("type", type);
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
//        User currentUser = TokenUtils.getCurrentUser();
//        if (currentUser.getRole().equals("ROLE_USER")) {
//            queryWrapper.eq("user", currentUser.getUsername());
//        }
        return Result.success(courseService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

