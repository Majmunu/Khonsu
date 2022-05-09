package com.halo.khonsu.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.halo.khonsu.common.Result;
import com.halo.khonsu.entity.Article;
import com.halo.khonsu.entity.Type;
import com.halo.khonsu.service.IArticleService;
import com.halo.khonsu.service.ITypeService;
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
@RequestMapping("/article")
public class ArticleController {





    @Resource
    private IArticleService articleService;
    @Resource
    private ITypeService typeService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Article article) {
        if(article.getId()==null){//新增
           article.setTime(DateUtil.now());
           article.setUser(TokenUtils.getCurrentUser().getNickname());
            article.setAvatarUrl(TokenUtils.getCurrentUser().getAvatarUrl());

        }
        articleService.saveOrUpdate(article);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        articleService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        articleService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(articleService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(articleService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam String name,
                           @RequestParam Integer pageNum,
                           @RequestParam(defaultValue = "") String typeid,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByDesc("id");
        if(StrUtil.isNotBlank(name)){
            queryWrapper.like("name",name);
        }
        if (!"".equals(typeid)) {
            queryWrapper.like("typeid", typeid);
        }
        Page<Article> page=articleService.page(new Page<>(pageNum,pageSize),queryWrapper);
        List<Article> records=page.getRecords();
        for(Article record:records){
            Type type1=typeService.getById(record.getTypeid());
            if(type1!=null){
                record.setType(type1.getTypename());
            }
        }



        /*Page<Article> page = articleService.page(new Page<>(pageNum, pageSize), queryWrapper);*/
        return Result.success(page);
    }


}

