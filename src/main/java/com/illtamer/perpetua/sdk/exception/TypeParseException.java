package com.illtamer.perpetua.sdk.exception;

public class TypeParseException extends RuntimeException {

    public TypeParseException() {
    }

    public TypeParseException(String message) {
        super(message);
    }

    public TypeParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
