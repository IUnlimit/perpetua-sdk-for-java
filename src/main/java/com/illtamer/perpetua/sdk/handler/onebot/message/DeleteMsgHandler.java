package com.illtamer.perpetua.sdk.handler.onebot.message;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;
import lombok.Getter;

/**
 * 撤回消息 APIHandler
 * */
@Getter
public class DeleteMsgHandler extends AbstractWSAPIHandler<Object> {

    @SerializedName("message_id")
    private Integer messageId;

    public DeleteMsgHandler() {
        super("delete_msg");
    }

    public DeleteMsgHandler setMessageId(Integer messageId) {
        this.messageId = messageId;
        return this;
    }

}
