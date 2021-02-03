package com.zoo.member.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoo.member.entity.Area;
import com.zoo.member.mapper.AreaMapper;
import com.zoo.member.service.AreaService;
import org.springframework.stereotype.Service;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {
}
