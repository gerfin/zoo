package com.zoo.member.controller;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.zoo.common.config.WebLogInterceptor;
import com.zoo.common.entity.Response;
import com.zoo.member.entity.Area;
import com.zoo.member.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/area")
@RestController
public class AreaController {
    private AreaService areaService;
    private final static ILogger LOGGER = SLoggerFactory.getLogger(AreaController.class);

    @Autowired
    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @PostMapping
    public ResponseEntity<Response> store(Area area) {
        areaService.save(area);
        LOGGER.info("新增area", "requestId", WebLogInterceptor.getRequestId());
        return Response.ok();
    }
}
