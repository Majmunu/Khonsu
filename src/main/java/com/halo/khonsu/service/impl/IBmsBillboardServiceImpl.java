package com.halo.khonsu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.khonsu.mapper.BmsBillboardMapper ;
import com.halo.khonsu.entity.BmsBillboard;
import com.halo.khonsu.service.IBmsBillboardService ;
import org.springframework.stereotype.Service;

@Service
public class IBmsBillboardServiceImpl extends ServiceImpl<BmsBillboardMapper
        , BmsBillboard> implements IBmsBillboardService {

}
