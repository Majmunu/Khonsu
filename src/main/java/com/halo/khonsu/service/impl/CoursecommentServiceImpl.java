package com.halo.khonsu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.khonsu.entity.Coursecomment;
import com.halo.khonsu.mapper.CoursecommentMapper;
import com.halo.khonsu.service.ICoursecommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chen
 * @since 2022-05-17
 */
@Service
public class CoursecommentServiceImpl extends ServiceImpl<CoursecommentMapper, Coursecomment> implements ICoursecommentService {
    @Resource
    private CoursecommentMapper coursecommentMapper;
    @Override
    public List<Coursecomment> findCommentDetail(Integer courseId) {
        return  coursecommentMapper.findCommentDetail(courseId);
    }
}
