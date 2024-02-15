package com.illtamer.perpetua.sdk.handler.onebot.message;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.entity.transfer.entity.MessageEntity;
import com.illtamer.perpetua.sdk.event.EventResolver;
import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * 获取消息
 * */
@Getter
public class GetMsgHandler extends AbstractWSAPIHandler<Map<String, Object>> {

    @SerializedName("message_id")
    private Long messageId;

    public GetMsgHandler() {
        super("get_msg");
    }

    public GetMsgHandler setMessageId(Long messageId) {
        this.messageId = messageId;
        return this;
    }

    public static MessageEntity parse(@NotNull Response<Map<String, Object>> response) {
        return EventResolver.GSON.fromJson(new Gson().toJson(response.getData()), MessageEntity.class);
    }

}
