package com.illtamer.perpetua.sdk.handler.onebot.message;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.handler.onebot.AbstractAPIHandler;
import lombok.Getter;

/**
 * 撤回消息 APIHandler
 * */
@Getter
public class DeleteMsgHandler extends AbstractAPIHandler<Object> {

    @SerializedName("message_id")
    private Long messageId;

    public DeleteMsgHandler() {
        super("delete_msg");
    }

    public DeleteMsgHandler setMessageId(Long messageId) {
        this.messageId = messageId;
        return this;
    }

}