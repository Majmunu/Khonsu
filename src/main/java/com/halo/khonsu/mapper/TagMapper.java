package com.halo.khonsu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.halo.khonsu.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2022-05-07
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {


    void setArticleTag(@Param("tagId") Integer tagId, @Param("articleId") Integer articleId);
    void deleteArticleTag(@Param("tagId") Integer tagId, @Param("articleId") Integer articleId);
}
