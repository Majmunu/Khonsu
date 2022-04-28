package com.halo.khonsu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.halo.khonsu.common.Constants;
import com.halo.khonsu.common.Result;
import com.halo.khonsu.entity.Dict;
import com.halo.khonsu.entity.Menu;
import com.halo.khonsu.mapper.DictMapper;
import com.halo.khonsu.service.IMenuService;
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
@RequestMapping("/menu")
public class MenuController {


   @Resource
   private IMenuService menuService;

   @Resource
   private DictMapper dictMapper;

// 新增或者更新
@PostMapping
public Result save(@RequestBody Menu menu) {
   menuService.saveOrUpdate(menu);
        return Result.success();
        }

@DeleteMapping("/{id}")
public Result delete(@PathVariable Integer id) {
   menuService.removeById(id);
        return Result.success();
        }

@PostMapping("/del/batch")
public Result deleteBatch(@RequestBody List<Integer> ids) {
   menuService.removeByIds(ids);
        return Result.success();
        }

@GetMapping

public Result findAll(@RequestParam(defaultValue = "") String name) {



        return Result.success(menuService.findMenus(name));
        }


    @GetMapping("/ids")
    public Result findAllIds() {
        return Result.success(menuService.list().stream().map(Menu::getId));
    }


    @GetMapping("/{id}")
public Result findOne(@PathVariable Integer id) {
        return Result.success(menuService.getById(id));
        }

@GetMapping("/page")
public Result findPage(@RequestParam String name,
                       @RequestParam Integer pageNum,
                       @RequestParam Integer pageSize) {
    QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
    queryWrapper.like("name",name);
    queryWrapper.orderByDesc("id");
    return Result.success(menuService.page(new Page<>(pageNum, pageSize), queryWrapper));
}
    @GetMapping("/icons")
    public Result getIcons() {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success(dictMapper.selectList(queryWrapper));
    }


}

