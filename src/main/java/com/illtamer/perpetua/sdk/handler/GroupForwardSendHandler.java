package com.illtamer.perpetua.sdk.handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.message.Message;
import lombok.Getter;

import java.util.Map;

/**
 * 合并转发消息 APIHandler
 * */
@Getter
public class GroupForwardSendHandler extends AbstractAPIHandler<Map<String, Object>> {

    @SerializedName("group_id")
    private Long groupId;

    private JsonArray messages;

    public GroupForwardSendHandler() {
        super("/send_group_forward_msg");
    }

    public GroupForwardSendHandler setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public GroupForwardSendHandler setMessages(Message message) {
        this.messages = new Gson().fromJson(message.toString(), JsonArray.class);
        return this;
    }

    public GroupForwardSendHandler setMessages(JsonArray messages) {
        this.messages = messages;
        return this;
    }

}
