package com.zoo.member.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MemberCustomExceptionEnum {
    HAVE_REGISTERED_YET(301, "该账号已注册"),
    HAVE_NOT_REGISTERED_YET(302, "该手机号未注册"),
    PASSWORD_INCORRECT(303, "输入的密码有误")
        ;

    private final int code;
    private final String msg;

    @Override
    public String toString() {
        return msg + "(" + code + ")";
    }
}
