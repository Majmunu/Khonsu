package com.halo.khonsu.service.impl;

import com.halo.khonsu.entity.Question;
import com.halo.khonsu.mapper.QuestionMapper;
import com.halo.khonsu.service.IQuestionService;
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
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

}
