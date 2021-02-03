package com.zoo.post.controller;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.zoo.common.annotation.NeedLogin;
import com.zoo.common.config.WebLogInterceptor;
import com.zoo.common.entity.LoginMember;
import com.zoo.common.entity.Response;
import com.zoo.post.entity.Post;
import com.zoo.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/post")
@RestController
public class PostController {
    private final PostService postService;
    private final static ILogger LOGGER = SLoggerFactory.getLogger(PostController.class);

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @NeedLogin
    @PostMapping
    public ResponseEntity<Response> store(Post post) {
        post.setMemberId(LoginMember.getId());
        postService.save(post);
        LOGGER.info("新增文章", "requestId", WebLogInterceptor.getRequestId());
        return Response.ok();
    }
}
