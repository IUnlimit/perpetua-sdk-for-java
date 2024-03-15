package com.illtamer.perpetua.sdk.event.notice;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.handler.OpenAPIHandling;
import com.illtamer.perpetua.sdk.message.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 好友通知上报事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
public abstract class FriendNoticeEvent extends NoticeEvent {

    /**
     * 好友 QQ
     * */
    @SerializedName("user_id")
    private Long userId;

    /**
     * 向该消息发送者发送消息
     * @return 消息 ID
     * */
    public Integer sendMessage(String message) {
        return OpenAPIHandling.sendMessage(message, userId);
    }

    /**
     * 向该消息发送者发送消息
     * @return 消息 ID
     * */
    public Integer sendMessage(Message message) {
        return OpenAPIHandling.sendMessage(message, userId);
    }

}
