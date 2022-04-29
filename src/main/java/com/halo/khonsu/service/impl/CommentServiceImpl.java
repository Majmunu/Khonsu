package com.halo.khonsu.service.impl;

import com.halo.khonsu.entity.Comment;
import com.halo.khonsu.mapper.CommentMapper;
import com.halo.khonsu.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chen
 * @since 2022-04-29
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;
    @Override
    public List<Comment> findCommentDetail(Integer articleId) {
        return commentMapper.findCommentDetail(articleId);
    }
}
