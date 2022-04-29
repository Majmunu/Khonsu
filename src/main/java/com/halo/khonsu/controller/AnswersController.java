package com.halo.khonsu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.halo.khonsu.common.Result;
import com.halo.khonsu.entity.Answers;
import com.halo.khonsu.service.IAnswersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chen
 * @since 2022-04-29
 */
@RestController
@RequestMapping("/answers")
public class AnswersController {


    @Resource
    private IAnswersService answersService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Answers answers) {
        answersService.saveOrUpdate(answers);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        answersService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        answersService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(answersService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(answersService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Answers> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(answersService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

