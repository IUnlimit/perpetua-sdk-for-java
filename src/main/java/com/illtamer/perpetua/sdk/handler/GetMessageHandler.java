package com.illtamer.perpetua.sdk.handler;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.entity.transfer.receive.MessageEntity;
import com.illtamer.perpetua.sdk.event.EventResolver;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * 获取消息 APIHandler
 * */
@Getter
public class GetMessageHandler extends AbstractAPIHandler<Map<String, Object>> {

    @SerializedName("message_id")
    private Integer messageId;

    public GetMessageHandler() {
        super("/get_msg");
    }

    public GetMessageHandler setMessageId(Integer messageId) {
        this.messageId = messageId;
        return this;
    }

    public static MessageEntity parse(@NotNull Response<Map<String, Object>> response) {
        return EventResolver.GSON.fromJson(new Gson().toJson(response.getData()), MessageEntity.class);
    }

}
