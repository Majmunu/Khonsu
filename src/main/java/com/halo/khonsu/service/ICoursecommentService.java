package com.halo.khonsu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.halo.khonsu.entity.Coursecomment;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chen
 * @since 2022-05-17
 */
public interface ICoursecommentService extends IService<Coursecomment> {
    List<Coursecomment> findCommentDetail(Integer courseId);

}
