package com.zoo.post.controller;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.zoo.common.config.WebLogInterceptor;
import com.zoo.common.entity.Response;
import com.zoo.post.entity.Category;
import com.zoo.post.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;
    private final static ILogger LOGGER = SLoggerFactory.getLogger(CategoryController.class);

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Response> store(Category category) {
        categoryService.save(category);
        LOGGER.info("新增分类", "requestId", WebLogInterceptor.getRequestId());
        return Response.ok();
    }
}
