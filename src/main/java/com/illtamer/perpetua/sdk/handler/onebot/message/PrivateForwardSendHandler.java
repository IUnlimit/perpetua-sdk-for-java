package com.illtamer.perpetua.sdk.handler.onebot.message;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;
import com.illtamer.perpetua.sdk.message.Message;
import lombok.Getter;

import java.util.Map;

/**
 * 合并转发消息 APIHandler
 * */
@Getter
@Deprecated
public class PrivateForwardSendHandler extends AbstractWSAPIHandler<Map<String, Object>> {

    @SerializedName("user_id")
    private Long userId;

    private JsonArray messages;

    public PrivateForwardSendHandler() {
        super("send_private_forward_msg");
    }

    public PrivateForwardSendHandler setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public PrivateForwardSendHandler setMessages(Message message) {
        this.messages = new Gson().fromJson(message.toString(), JsonArray.class);
        return this;
    }

    public PrivateForwardSendHandler setMessages(JsonArray messages) {
        this.messages = messages;
        return this;
    }

}
