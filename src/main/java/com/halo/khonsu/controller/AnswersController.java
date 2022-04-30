package com.halo.khonsu.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.halo.khonsu.common.Result;
import com.halo.khonsu.entity.Answers;
import com.halo.khonsu.service.IAnswersService;
import com.halo.khonsu.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (answers.getId() == null) { // 新增评论
            answers.setUserId(TokenUtils.getCurrentUser().getId());
            answers.setTime(DateUtil.now());

            if (answers.getPid() != null) {  // 判断如果是回复，进行处理
                Integer pid = answers.getPid();
                Answers pComment = answersService.getById(pid);
                if (pComment.getOriginId() != null) {  // 如果当前回复的父级有祖宗，那么就设置相同的祖宗
                    answers.setOriginId(pComment.getOriginId());
                } else {  // 否则就设置父级为当前回复的祖宗
                    answers.setOriginId(answers.getPid());
                }
            }

        }
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

    @GetMapping("/tree/{questionId}")
    public Result findTree(@PathVariable Integer questionId) {
        List<Answers> questionComments = answersService.findCommentDetail(questionId); // 查询所有的评论和回复数据
        // 查询评论数据（不包括回复）
        List<Answers> originList = questionComments.stream().filter(answers -> answers.getOriginId() == null).collect(Collectors.toList());

        // 设置评论数据的子节点，也就是回复内容
        for (Answers origin : originList) {
            List<Answers> AnswersComments = questionComments.stream().filter(answers -> origin.getId().equals(answers.getOriginId())).collect(Collectors.toList());  // 表示回复对象集合
            AnswersComments.forEach(answers -> {
                Optional<Answers> pComment = questionComments.stream().filter(c1 -> c1.getId().equals(answers.getPid())).findFirst();  // 找到当前评论的父级
                pComment.ifPresent((v -> {  // 找到父级评论的用户id和用户昵称，并设置给当前的回复对象
                    answers.setPUserId(v.getUserId());
                    answers.setPNickname(v.getNickname());
                }));
            });
            origin.setChildren(AnswersComments);
        }
        return Result.success(originList);
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

