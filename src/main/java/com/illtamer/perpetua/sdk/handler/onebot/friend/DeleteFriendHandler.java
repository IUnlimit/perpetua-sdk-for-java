package com.illtamer.perpetua.sdk.handler.onebot.friend;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.handler.onebot.AbstractAPIHandler;
import lombok.Getter;

import java.util.Map;

/**
 * 删除好友
 * */
@Getter
@Deprecated
public class DeleteFriendHandler extends AbstractAPIHandler<Map<String, Object>> {

    /**
     * 好友 QQ 号
     * */
    @SerializedName("friend_id")
    private Long friendId;

    public DeleteFriendHandler() {
        super("delete_friend");
    }

    public DeleteFriendHandler setFriendId(Long friendId) {
        this.friendId = friendId;
        return this;
    }

}
