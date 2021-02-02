package com.zoo.member.service;

import com.zoo.member.MemberApplication;
import com.zoo.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MemberApplication.class)
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    void register() {
        Member member = new Member();
        member.setUsername("小明");
        member.setPassword("123456");
        authService.register(member);
    }
}