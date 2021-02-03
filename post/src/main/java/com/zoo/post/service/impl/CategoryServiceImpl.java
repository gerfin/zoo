package com.zoo.post.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoo.post.entity.Category;
import com.zoo.post.mapper.CategoryMapper;
import com.zoo.post.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
