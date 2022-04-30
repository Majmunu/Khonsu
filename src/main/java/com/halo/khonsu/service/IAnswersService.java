package com.halo.khonsu.service;

import com.halo.khonsu.entity.Answers;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chen
 * @since 2022-04-29
 */
public interface IAnswersService extends IService<Answers> {

    List<Answers> findCommentDetail(Integer questionId);
}
