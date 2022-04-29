package com.halo.khonsu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.halo.khonsu.entity.Comment;
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
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("select c.*,u.nickname,u.avatar_url from t_comment c left join sys_user u on c.user_id = u.id " +
                   "where c.article_id = #{articleId} order by id desc")
    List<Comment> findCommentDetail(@Param("articleId") Integer articleId);
}
