package com.zoo.common.entity;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.zoo.common.config.WebLogInterceptor;

public class LoginMember {
    private static final ThreadLocal<Member> memberThreadLocal = new ThreadLocal<>();
    private static final ILogger LOGGER = SLoggerFactory.getLogger(LoginMember.class);

    /**
     * 在request线程中保存用户信息
     * @param member 用户信息
     */
    public static void setMemberThreadLocal(Member member) {
        LOGGER.info("request存入用户信息", "requestId", WebLogInterceptor.getRequestId(), "member", member);
        memberThreadLocal.set(member);
    }

    /**
     * 获取当前request的登录用户信息
     * @return 当前登录用户信息
     */
    public static Member getLoginMember() {
        return memberThreadLocal.get();
    }

    public static Long getId() {
        Member member = memberThreadLocal.get();
        return member.getId();
    }
}
