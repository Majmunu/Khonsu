package com.halo.khonsu.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.halo.khonsu.common.Result;
import com.halo.khonsu.entity.Question;
import com.halo.khonsu.service.IQuestionService;
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
 * @since 2022-04-29
 */
@RestController
@RequestMapping("/question")
public class QuestionController {


    @Resource
    private IQuestionService questionService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Question question) {

        if(question.getId()==null){//新增
            question.setTime(DateUtil.now());
            question.setUser(TokenUtils.getCurrentUser().getNickname());
            question.setAvatarUrl(TokenUtils.getCurrentUser().getAvatarUrl());
        }

        questionService.saveOrUpdate(question);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        questionService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        questionService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(questionService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(questionService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if(StrUtil.isNotBlank(name)){
            queryWrapper.like("name",name);
        }
        return Result.success(questionService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

