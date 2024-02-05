package com.illtamer.perpetua.sdk.handler.onebot.friend;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.handler.onebot.AbstractAPIHandler;
import lombok.Getter;

/**
 * 发送好友赞
 * */
@Getter
public class SendLikeHandler extends AbstractAPIHandler<Object> {

    /**
     * 对方账号
     * */
    @SerializedName("user_id")
    private Long userId;

    /**
     * 赞的次数
     * <p>
     * 每个好友每天最多 10 次
     * */
    private Integer times;

    public SendLikeHandler() {
        super("send_like");
    }

    public SendLikeHandler setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public SendLikeHandler setTimes(Integer times) {
        this.times = times;
        return this;
    }

}
