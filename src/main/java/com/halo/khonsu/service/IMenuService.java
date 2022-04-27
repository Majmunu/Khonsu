package com.halo.khonsu.service;

import com.halo.khonsu.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chen
 * @since 2022-04-26
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
