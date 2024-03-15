package com.illtamer.perpetua.sdk.handler.onebot.message;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;
import lombok.Getter;

import java.util.Map;

/**
 * 获取合并转发内容
 * */
@Getter
@Deprecated
public class GetForwardMsgHandler extends AbstractWSAPIHandler<Map<String, Object>> {

    @SerializedName("message_id")
    private Integer messageId;

    public GetForwardMsgHandler() {
        super("get_forward_msg");
    }

    public GetForwardMsgHandler setMessageId(Integer messageId) {
        this.messageId = messageId;
        return this;
    }

}
