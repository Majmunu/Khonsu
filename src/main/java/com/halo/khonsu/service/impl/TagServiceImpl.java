package com.halo.khonsu.service.impl;

import com.halo.khonsu.entity.Tag;
import com.halo.khonsu.mapper.TagMapper;
import com.halo.khonsu.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chen
 * @since 2022-05-07
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}
