package com.illtamer.perpetua.sdk.exception;

/**
 * 消息节点类型冲突异常
 * */
public class ExclusiveMessageException extends RuntimeException {

    public ExclusiveMessageException(String type) {
        super("则当前消息节点中不可再存在其他消息，冲突的消息类型: " + type);
    }

}
