package com.halo.khonsu.service;

import com.halo.khonsu.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chen
 * @since 2022-05-07
 */
public interface ITagService extends IService<Tag> {


    void setArticleTag(Integer tagId, Integer articleId);

}
