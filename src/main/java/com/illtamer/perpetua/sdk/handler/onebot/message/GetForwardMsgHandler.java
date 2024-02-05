package com.illtamer.perpetua.sdk.handler.onebot.message;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.handler.onebot.AbstractAPIHandler;
import lombok.Getter;

import java.util.Map;

/**
 * 获取合并转发内容
 * */
@Getter
@Deprecated
public class GetForwardMsgHandler extends AbstractAPIHandler<Map<String, Object>> {

    @SerializedName("message_id")
    private Long messageId;

    public GetForwardMsgHandler() {
        super("get_forward_msg");
    }

    public GetForwardMsgHandler setMessageId(Long messageId) {
        this.messageId = messageId;
        return this;
    }

}
