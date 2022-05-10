package com.halo.khonsu.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.khonsu.entity.Validation;
import com.halo.khonsu.mapper.ValidationMapper;
import com.halo.khonsu.service.IValidationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chen
 * @since 2022-05-10
 */
@Service
public class ValidationServiceImpl extends ServiceImpl<ValidationMapper, Validation> implements IValidationService {

    @Override
    public void saveCode(String email, String code, Integer type, DateTime expireDate) {
        Validation validation=new Validation();
        validation.setEmail(email);
        validation.setCode(code);
        validation.setType(type);
        validation.setTime(expireDate);

        //下一次验证之前，删掉同类型的验证
        UpdateWrapper<Validation> validationUpdateWrapper = new UpdateWrapper<>();
        validationUpdateWrapper.eq("email", email);
        validationUpdateWrapper.eq("type", type);
        remove(validationUpdateWrapper);
        // 再插入新的code
        save(validation);
    }
}
