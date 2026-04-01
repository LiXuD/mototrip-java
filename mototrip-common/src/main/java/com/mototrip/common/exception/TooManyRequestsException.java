package com.mototrip.common.exception;

import lombok.Getter;

@Getter
public class TooManyRequestsException extends BusinessException {
    public TooManyRequestsException(String message) {
        super(429, message);
    }
}
