package com.illtamer.perpetua.sdk.event.message;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.entity.transfer.entity.AnonymousEntity;
import com.illtamer.perpetua.sdk.entity.transfer.entity.GroupMessageSender;
import com.illtamer.perpetua.sdk.handler.OpenAPIHandling;
import com.illtamer.perpetua.sdk.message.Message;
import com.illtamer.perpetua.sdk.message.MessageBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 群消息事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.MESSAGE,
        secType = "group",
        subType = {"normal", "anonymous", "notice"}
)
public class GroupMessageEvent extends MessageEvent {

    /**
     * 表示消息的子类型
     * <p>
     * 正常群聊消息是 normal, 匿名消息是 anonymous, 系统提示 ( 如「管理员已禁止群内匿名聊天」 ) 是 notice
     * */
    @SerializedName("sub_type")
    private String subType;

    /**
     * 群号
     * */
    @SerializedName("group_id")
    private Long groupId;

    /**
     * 发送者信息
     * */
    private GroupMessageSender sender;

    /**
     * 匿名信息, 如果不是匿名消息则为 null
     * */
    private AnonymousEntity anonymous;

    /**
     * 回复该条消息
     * */
    @Override
    public void reply(Message message) {
        reply(message, true);
    }

    /**
     * 回复该条消息
     * */
    public void reply(Message message, boolean atSender) {
        final MessageBuilder builder = MessageBuilder.json().reply(getMessageId());
        if (atSender)
            builder.at(getUserId());
        builder.addAll(message);
        OpenAPIHandling.sendGroupMessage(builder.build(), groupId);
    }

    /**
     * 撤回该条消息
     * @deprecated 快速操作API不稳定
     * */
    public void recall() {
        OpenAPIHandling.deleteMessage(getMessageId());
    }

    /**
     * 把发送者踢出群组 (需要登录号权限足够)
     * <p>
     * 不拒绝此人后续加群请求, 发送者是匿名用户时无效
     * @deprecated 快速操作API不稳定
     * */
    @Deprecated
    public void kick() {
        OpenAPIHandling.groupKick(groupId, getUserId());
    }

    /**
     * 把发送者禁言30分钟
     * <p>
     * 对匿名用户无效
     * */
    public void ban() {
        ban(30);
    }

    /**
     * 把发送者禁言指定时长
     * <p>
     * 对匿名用户也无效
     * */
    public void ban(int minute) {
        OpenAPIHandling.groupBan(groupId, getUserId(), minute * 60);
    }

    /**
     * 向该消息发送者发送消息
     * @return 消息 ID
     * */
    public Integer sendGroupMessage(String message) {
        return OpenAPIHandling.sendGroupMessage(message, groupId);
    }

    /**
     * 向该消息发送者发送消息
     * @return 消息 ID
     * */
    public Integer sendGroupMessage(Message message) {
        return OpenAPIHandling.sendGroupMessage(message, groupId);
    }

    public GroupMessageSender getSender() {
        sender.setGroupId(groupId);
        return sender;
    }

}
