package com.halo.khonsu.mapper;

import com.halo.khonsu.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2022-04-20
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
