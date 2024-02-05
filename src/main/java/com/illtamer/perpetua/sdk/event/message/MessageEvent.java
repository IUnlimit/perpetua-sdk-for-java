package com.illtamer.perpetua.sdk.event.message;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.entity.transfer.entity.MessageSender;
import com.illtamer.perpetua.sdk.event.Cancellable;
import com.illtamer.perpetua.sdk.event.Event;
import com.illtamer.perpetua.sdk.handler.OpenAPIHandling;
import com.illtamer.perpetua.sdk.message.Message;
import com.illtamer.perpetua.sdk.message.MessageBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 消息上报事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.MESSAGE,
        secType = "*"
)
public class MessageEvent extends Event implements Cancellable {

    private transient boolean cancelled;

    /**
     * 消息 ID
     * */
    @SerializedName("message_id")
    private Long messageId;

    /**
     * 消息类型
     * <p>
     * group, private
     * */
    @SerializedName("message_type")
    private String messageType;

    /**
     * 发送者 QQ 号
     * */
    @SerializedName("user_id")
    private Long userId;

    /**
     * 一个消息链
     * */
    private Message message;

    /**
     * CQ 码格式的消息
     * */
    @SerializedName("raw_message")
    private String rawMessage;

    /**
     * 字体
     * */
    private Integer font;

    /**
     * 发送者信息
     * */
    private transient MessageSender sender;

    /**
     * 回复该消息
     * */
    public void reply(String message) {
        reply(MessageBuilder.json().text(message).build());
    }

    /**
     * 回复该消息
     * */
    public void reply(Message message) {
        throw new UnsupportedOperationException();
    }

    /**
     * 向该消息发送者发送消息
     * @return 消息 ID
     * */
    public Long sendMessage(String message) {
        return OpenAPIHandling.sendMessage(message, userId);
    }

    /**
     * 向该消息发送者发送消息
     * @return 消息 ID
     * */
    public Long sendMessage(Message message) {
        return OpenAPIHandling.sendMessage(message, userId);
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

}
