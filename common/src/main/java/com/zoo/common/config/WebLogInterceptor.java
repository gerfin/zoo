package com.zoo.common.config;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.zoo.common.util.NetUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WebLogInterceptor implements HandlerInterceptor {

    private final ILogger LOGGER = SLoggerFactory.getLogger(WebLogInterceptor.class);
    private static ThreadLocal<String> requestIdThreadLocal = new ThreadLocal();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = UUID.randomUUID().toString();
        requestIdThreadLocal.set(requestId);

        LOGGER.info("REQUEST","requestId", requestId,"URL", request.getRequestURL().toString());
        // 打印 Content-Type
        LOGGER.info("REQUEST","requestId", requestId, "Content-Type", request.getHeader("Content-Type"));
        // 打印 Http method
        LOGGER.info("REQUEST","requestId", requestId,"HTTP-Method", request.getMethod());
        // 打印请求的 IP
        LOGGER.info("REQUEST","requestId", requestId,"IP", NetUtil.getIPAddress(request));
        // 打印请求入参（application/json特殊处理）
        try {
            if ("application/json".equals(request.getHeader("Content-Type"))) {
                String params = getRequestJson(request);
                LOGGER.info("REQUEST","requestId", requestId,"params", params);
            } else {
                Map paramMap = new HashMap();
                for (String key : request.getParameterMap().keySet()) {
                    paramMap.put(key,  request.getParameterMap().get(key)[0]);
                }
                LOGGER.info("REQUEST","requestId", requestId, "params", paramMap);
            }
        } catch (Exception e) {
            LOGGER.info("REQUEST","requestId", requestId, "Format-Params-Error", e);
        }


        return true;
    }

    private String getRequestJson(HttpServletRequest request) {

        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readlen = 0;
            try {
                readlen = request.getInputStream().read(buffer, i, contentLength - i);
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error("REQUEST","requestId", getRequestId(), "type", "解析json出错", "exception", e);
            }
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }

        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        try {
            return new String(buffer, charEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error("REQUEST","requestId", getRequestId(), "type", "buffer转String出错", "exception", e);
        }

        return null;
    }

    public static String getRequestId() {
        return requestIdThreadLocal.get();
    }
}
