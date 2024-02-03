package com.illtamer.perpetua.sdk.handler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.message.Message;
import lombok.Getter;

import java.util.Map;

@Getter
public class GroupMsgSendHandler extends AbstractAPIHandler<Map<String, Object>> {

    @SerializedName("group_id")
    private Long groupId;

    private JsonArray message;

    @SerializedName("auto_escape")
    private boolean autoEscape;

    public GroupMsgSendHandler() {
        super("/send_group_msg");
    }

    public GroupMsgSendHandler setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public GroupMsgSendHandler setMessage(JsonArray message) {
        this.message = message;
        return this;
    }

    public GroupMsgSendHandler setMessage(Message message) {
        this.message = new Gson().fromJson(message.toString(), JsonArray.class);
        this.autoEscape = message.isTextOnly();
        return this;
    }

}
