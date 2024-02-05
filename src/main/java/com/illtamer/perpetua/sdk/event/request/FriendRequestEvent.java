package com.illtamer.perpetua.sdk.event.request;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.event.QuickAction;
import com.illtamer.perpetua.sdk.handler.OpenAPIHandling;
import com.illtamer.perpetua.sdk.handler.onebot.QuickActionHandler;
import com.illtamer.perpetua.sdk.message.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

/**
 * 加好友请求事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.REQUEST,
        secType = "friend"
)
public class FriendRequestEvent extends RequestEvent implements QuickAction {

    /**
     * 发送请求的 QQ 号
     * */
    @SerializedName("user_id")
    private Long userId;

    /**
     * 验证信息
     * */
    private String comment;

    /**
     * 请求 flag, 在调用处理请求的 API 时需要传入
     * */
    private String flag;

    /**
     * 同意请求
     * @param remark 添加后的好友备注 (仅在同意时有效)
     * */
    public void approve(@Nullable String remark) {
        QuickActionHandler handler = new QuickActionHandler(this)
                .addOperation("approve", true);
        if (remark != null)
            handler.addOperation("remark", remark);
        handler.request();
    }

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
