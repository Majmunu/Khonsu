package com.halo.khonsu.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.halo.khonsu.common.Result;
import com.halo.khonsu.entity.Comment;
import com.halo.khonsu.service.ICommentService;
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
@RequestMapping("/comment")
public class CommentController {


    @Resource
    private ICommentService commentService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Comment comment) {

        if (comment.getId() == null) { // 新增评论
            comment.setUserId(TokenUtils.getCurrentUser().getId());
            comment.setTime(DateUtil.now());

            /*if (comment.getPid() != null) {  // 判断如果是回复，进行处理
                Integer pid = comment.getPid();
                Comment pComment = commentService.getById(pid);
                if (pComment.getOriginId() != null) {  // 如果当前回复的父级有祖宗，那么就设置相同的祖宗
                    comment.setOriginId(pComment.getOriginId());
                } else {  // 否则就设置父级为当前回复的祖宗
                    comment.setOriginId(comment.getPid());
                }
            }*/

        }


        commentService.saveOrUpdate(comment);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        commentService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        commentService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(commentService.list());
    }

    @GetMapping("/tree/{articleId}")
    public Result findTree(@PathVariable Integer articleId) {

        List<Comment> articleComments = commentService.findCommentDetail(articleId);
        return Result.success(articleComments);
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(commentService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(commentService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

}

