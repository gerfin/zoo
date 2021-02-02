package com.zoo.common.entity;

import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
public class Response {
    public final static int SUCCESS_CODE = 200;
    public final static String SUCCESS_MSG = "请求成功";
    public final static int FAIL_CODE = 400;
    public final static String FAIL_MSG = "请求失败";


    private int code;
    private String msg;
    private Object data;
    private Object meta;

    private Response() {
    }

    private Response(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Response(int code, String msg, Object data, Object meta) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.meta = meta;
    }

    public static ResponseEntity<Response> ok() {
        Response result = new Response(SUCCESS_CODE, SUCCESS_MSG, null);
        return ResponseEntity.ok(result);
    }

    public static ResponseEntity<Response> ok(Object data) {
        Response result = new Response(SUCCESS_CODE, SUCCESS_MSG, data);
        return ResponseEntity.ok(result);
    }

    public static ResponseEntity<Response> ok(Object data, Object meta) {
        Response result = new Response(SUCCESS_CODE, SUCCESS_MSG, data, meta);
        return ResponseEntity.ok(result);
    }

    public static ResponseEntity<Response> fail() {
        Response result = new Response(FAIL_CODE, FAIL_MSG, null);
        return ResponseEntity.badRequest().body(result);
    }

    public static ResponseEntity<Response> fail(String msg) {
        Response result = new Response(FAIL_CODE, msg, null);
        return ResponseEntity.ok(result);
    }


}
