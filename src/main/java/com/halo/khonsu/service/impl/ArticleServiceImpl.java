package com.halo.khonsu.service.impl;

import com.halo.khonsu.entity.Article;
import com.halo.khonsu.mapper.ArticleMapper;
import com.halo.khonsu.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chen
 * @since 2022-04-29
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
