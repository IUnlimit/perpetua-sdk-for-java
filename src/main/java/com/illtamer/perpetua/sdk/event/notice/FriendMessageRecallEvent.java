package com.illtamer.perpetua.sdk.event.notice;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.handler.OpenAPIHandling;
import com.illtamer.perpetua.sdk.message.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 好友消息撤回事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.NOTICE,
        secType = "friend_recall"
)
public class FriendMessageRecallEvent extends NoticeEvent {

    /**
     * 好友 QQ 号
     * */
    @SerializedName("user_id")
    private Long userId;

    /**
     * 被撤回的消息 ID
     * */
    @SerializedName("message_id")
    private Integer messageId;

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
