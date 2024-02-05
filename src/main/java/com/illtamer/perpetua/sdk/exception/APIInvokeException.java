package com.illtamer.perpetua.sdk.exception;

import com.illtamer.perpetua.sdk.Response;

/**
 * API 调用异常
 * */
public class APIInvokeException extends RuntimeException {

    public APIInvokeException(Response<?> response) {
        super(String.format("API 不支持或调用异常(retcode: %d): %s", response.getRetcode(), response.getMsg()));
    }

    public APIInvokeException(Response<?> response, Throwable cause) {
        super(response.getMsg(), cause);
    }

}
