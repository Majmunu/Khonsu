package com.halo.khonsu.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.halo.khonsu.entity.Type;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2022-05-08
 */
@Mapper
public interface TypeMapper extends BaseMapper<Type> {

    Page<Type> findPage(Page<Type> page, @Param("typename") String typename);
}
