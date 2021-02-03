package com.zoo.post.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoo.post.entity.Post;
import com.zoo.post.mapper.PostMapper;
import com.zoo.post.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
}
