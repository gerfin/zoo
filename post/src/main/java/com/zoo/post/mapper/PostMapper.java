package com.zoo.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zoo.post.entity.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
}
