package com.halo.khonsu.service.impl;

import com.halo.khonsu.entity.Answers;
import com.halo.khonsu.mapper.AnswersMapper;
import com.halo.khonsu.service.IAnswersService;
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
public class AnswersServiceImpl extends ServiceImpl<AnswersMapper, Answers> implements IAnswersService {

    @Resource
    private AnswersMapper answersMapper;
    @Override
    public List<Answers> findCommentDetail(Integer questionId) {

        return  answersMapper.findCommentDetail(questionId);
    }
}
