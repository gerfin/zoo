package com.zoo.common.util;

import cn.hutool.crypto.SecureUtil;

public class CryptoUtil {
    public static String md5(String data) {
        return SecureUtil.md5(data);
    }
    public static String md5(String data, String salt) {
        return SecureUtil.md5(data + salt);
    }
}
