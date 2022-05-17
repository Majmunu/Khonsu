package com.halo.khonsu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.halo.khonsu.entity.Coursecomment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2022-05-17
 */
@Mapper
public interface CoursecommentMapper extends BaseMapper<Coursecomment> {
    @Select("select c.*,u.nickname,u.avatar_url from t_coursecomment c left join sys_user u on c.user_id = u.id " +
            "where c.course_id = #{courseId} order by id desc")
    List<Coursecomment> findCommentDetail(@Param("courseId") Integer courseId);
}
