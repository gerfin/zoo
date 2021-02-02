package com.zoo.common.exception;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {
    private int code;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(Enum e) {
        super(e.toString());
        this.code = e.hashCode();
    }
}
