package com.zoo.common.exception;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.zoo.common.config.WebLogInterceptor;
import com.zoo.common.entity.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalRestControllerAdvice {
    private final ILogger LOGGER = SLoggerFactory.getLogger(GlobalRestControllerAdvice.class);
    private final String INNER_EXCEPTION = "内部错误";

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Response> handleBindException(BindException bindException) {
        LOGGER.info("controller层验证不通过","requestId", WebLogInterceptor.getRequestId(), "msg", bindException.getMessage());
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError objectError : bindException.getAllErrors()) {
            errorMessages.add(objectError.getDefaultMessage());
        }
        return Response.fail(String.join(",", errorMessages));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Response> handleCustomException(CustomException customException) {
        LOGGER.info("CustomException","requestId", WebLogInterceptor.getRequestId(), "msg", customException.getMessage());
        return Response.fail(customException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleCommonException (Exception e) {
        LOGGER.info("统一报错","requestId", WebLogInterceptor.getRequestId(), "msg", e.getMessage());
        return Response.fail(INNER_EXCEPTION);
    }

}
