package com.illtamer.perpetua.sdk.event.message;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.entity.transfer.receive.MessageSender;
import com.illtamer.perpetua.sdk.event.QuickAction;
import com.illtamer.perpetua.sdk.handler.OpenAPIHandling;
import com.illtamer.perpetua.sdk.message.Message;
import com.illtamer.perpetua.sdk.message.MessageBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 私聊消息事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.MESSAGE,
        secType = "private",
        subType = {"friend", "group", "group_self", "other"}
)
public class PrivateMessageEvent extends MessageEvent implements QuickAction {

    /**
     * 表示消息的子类型
     * <p>
     * 如果是好友则是 friend, 如果是群临时会话则是 group, 如果是在群中自身发送则是 group_self
     * */
    @SerializedName("sub_type")
    private String subType;

    /**
     * 临时会话来源
     * */
    @SerializedName("temp_source")
    private Integer tempSource;

    /**
     * 发送者信息
     * */
    private MessageSender sender;

    /**
     * 快捷回复该条消息
     * */
    @Override
    public void reply(Message message) {
        final MessageBuilder builder = MessageBuilder.json().reply(getMessageId());
        builder.addAll(message);
        OpenAPIHandling.sendMessage(builder.build(), getUserId());
    }

}
