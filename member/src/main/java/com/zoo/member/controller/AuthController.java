package com.zoo.member.controller;

import com.zoo.common.entity.Response;
import com.zoo.member.entity.Member;
import com.zoo.member.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/member")
@RestController
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@Validated({Member.RegisterGroup.class}) Member member) {
        authService.register(member);
        return Response.ok();
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@Validated({Member.LoginGroup.class}) Member member) {
        Member result = authService.login(member);
        return Response.ok(result);
    }
}
