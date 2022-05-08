package com.halo.khonsu.service.impl;

import com.halo.khonsu.entity.Type;
import com.halo.khonsu.mapper.TypeMapper;
import com.halo.khonsu.service.ITypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
