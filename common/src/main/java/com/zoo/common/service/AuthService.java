package com.zoo.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.zoo.common.config.WebLogInterceptor;
import com.zoo.common.entity.LoginMember;
import com.zoo.common.entity.Member;
import com.zoo.common.util.CryptoUtil;
import com.zoo.common.util.RedisUtil;
import com.zoo.common.exception.MemberCustomExceptionEnum;
import com.zoo.common.repository.MemberRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import  com.zoo.common.exception.CustomException;
import java.util.UUID;

@Service
public class AuthService {
    @Value("${common.passwordEncodeSalt:salt12345678}")
    private String passwordEncodeSalt;
    @Value("${common.tokenExpireSecond:604800}")
    private Integer tokenExpireSecond;

    private final MemberRepository memberRepository;
    private static RedisUtil redisUtil;

    private final static ILogger LOGGER = SLoggerFactory.getLogger(AuthService.class);

    @Autowired
    public AuthService(MemberRepository memberRepository, RedisUtil redisUtil) {
        this.memberRepository = memberRepository;
        AuthService.redisUtil = redisUtil;
    }

    /**
     * 注册
     * @param member 用户信息
     */
    public void register(Member member) {
        validateNotRegisteredBefore(member);
        encodePasswordProp(member);
        memberRepository.save(member);
    }

    /**
     * 登录
     * @param member 用户信息
     * @return
     */
    public Member login(Member member) {
        encodePasswordProp(member);
        Member memberLogin = memberRepository.findByPhone(member.getPhone());
        if (memberLogin == null) {
            throw new CustomException(MemberCustomExceptionEnum.HAVE_NOT_REGISTERED_YET);
        }

        if (!memberLogin.getPassword().equals(member.getPassword())) {
            throw new CustomException(MemberCustomExceptionEnum.PASSWORD_INCORRECT);
        }

        generateToken(memberLogin);
        LoginMember.setMemberThreadLocal(memberLogin);
        return memberRepository.save(memberLogin);
    }

    /**
     * 确认用户之前没有注册过
     * @param member 用户信息
     */
    private void validateNotRegisteredBefore(Member member) {
        Long countMember = memberRepository.countByUsernameOrPhone(member.getUsername(), member.getPhone());
        LOGGER.info("确认用户之前是否登录", "requestId", WebLogInterceptor.getRequestId(), "isRegistered", countMember > 0);
        if (countMember > 0) {
            throw new CustomException(MemberCustomExceptionEnum.HAVE_REGISTERED_YET);
        }
    }

    /**
     * 对password属性进行加密
     * @param member 用户信息
     */
    private void encodePasswordProp(Member member) {
        LOGGER.info("对用户密码进行加密", "requestId", WebLogInterceptor.getRequestId());
        member.setPassword(
               CryptoUtil.md5(member.getPassword(), passwordEncodeSalt)
        );
    }

    /**
     * member生成并更新token
     * @param member 用户信息
     */
    private void generateToken(Member member) {
        String token = CryptoUtil.md5(UUID.randomUUID().toString() + member.getId());
        LOGGER.info("redis删除老token", "requestId", WebLogInterceptor.getRequestId());
        redisUtil.del(member.getAccessToken());
        member.setAccessToken(token);
        LOGGER.info("生成Token", "requestId", WebLogInterceptor.getRequestId(), "token", token);
        ObjectMapper mapper = new ObjectMapper();
        String memberJson = null;
        try {
            memberJson = mapper.writeValueAsString(member);
            LOGGER.info("member转json", "requestId", WebLogInterceptor.getRequestId(), "json", memberJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            LOGGER.error("生成token时序列化member失败", "requestId", WebLogInterceptor.getRequestId(), "member", member);
        }
        LOGGER.info("token存入redis", "requestId", WebLogInterceptor.getRequestId());
        redisUtil.set(token, memberJson, tokenExpireSecond);
    }

    /**
     * 通过token获取用户信息
     * @param token token
     * @return 用户信息
     */
    public static Member getMemberByToken(String token) {
        String memberJson = (String) redisUtil.get(token);
        LOGGER.info("从redis中获取到用户信息", "requestId", WebLogInterceptor.getRequestId(), "token", token, "json", memberJson);
        if (Strings.isNotBlank(memberJson)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(memberJson, Member.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                LOGGER.error("获取member时反序列化失败", "requestId", WebLogInterceptor.getRequestId(), "json", memberJson);
            }
        }
        return null;
    }



}
