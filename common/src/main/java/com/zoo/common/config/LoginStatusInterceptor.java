package com.zoo.common.config;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.zoo.common.annotation.NeedLogin;
import com.zoo.common.entity.LoginMember;
import com.zoo.common.entity.Member;
import com.zoo.common.exception.CustomException;
import com.zoo.common.exception.MemberCustomExceptionEnum;
import com.zoo.common.service.AuthService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录状态拦截
 */
public class LoginStatusInterceptor implements HandlerInterceptor {
    private final ILogger LOGGER = SLoggerFactory.getLogger(LoginStatusInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            HandlerMethod h = (HandlerMethod)handler;
            if (h.getMethod().getDeclaringClass().isAnnotationPresent(NeedLogin.class)
                    || h.hasMethodAnnotation(NeedLogin.class)) {
                String token = request.getHeader("Authorization");
                interceptMemberByToken(token);
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    /**
     * 拦截用户信息
     * @param token
     */
    private void interceptMemberByToken(String token) {
        LOGGER.info("用户信息拦截", "requestId", WebLogInterceptor.getRequestId(), "token", token);
        if (Strings.isBlank(token)) {
            throw new CustomException(MemberCustomExceptionEnum.TOKEN_EMPTY);
        } else {
            Member member = AuthService.getMemberByToken(token);
            LOGGER.info("用户信息", "requestId", WebLogInterceptor.getRequestId(), "member", member);
            if (member == null) {
                throw new CustomException(MemberCustomExceptionEnum.TOKEN_NOT_VALID);
            }
            LoginMember.setMemberThreadLocal(member);
        }
    }
}
