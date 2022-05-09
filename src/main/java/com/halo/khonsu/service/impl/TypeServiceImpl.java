package com.halo.khonsu.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.halo.khonsu.entity.Type;
import com.halo.khonsu.mapper.TypeMapper;
import com.halo.khonsu.service.ITypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chen
 * @since 2022-05-08
 */

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements ITypeService {

    @Resource
    private TypeMapper typeMapper;
    @Override
    public Page<Type> findPage(Page<Type> page, String typename) {

       return typeMapper.findPage(page,typename);
    }
}
