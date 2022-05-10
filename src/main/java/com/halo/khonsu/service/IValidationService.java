package com.halo.khonsu.service;

import cn.hutool.core.date.DateTime;
import com.halo.khonsu.entity.Validation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chen
 * @since 2022-05-10
 */
public interface IValidationService extends IService<Validation> {

    void saveCode(String email, String code, Integer type, DateTime expireDate);
}
