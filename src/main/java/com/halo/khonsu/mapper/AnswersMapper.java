package com.halo.khonsu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.halo.khonsu.entity.Answers;
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
 * @since 2022-04-29
 */
@Mapper
public interface AnswersMapper extends BaseMapper<Answers> {

    @Select("select c.*,u.nickname,u.avatar_url from t_answers c left join sys_user u on c.user_id = u.id " +
            "where c.question_id = #{questionId} order by id desc")
    List<Answers> findCommentDetail(@Param("questionId") Integer questionId);
}
