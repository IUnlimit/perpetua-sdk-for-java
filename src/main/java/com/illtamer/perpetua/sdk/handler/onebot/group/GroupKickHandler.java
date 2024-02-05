package com.illtamer.perpetua.sdk.handler.onebot.group;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.handler.onebot.AbstractAPIHandler;
import lombok.Getter;

/**
 * 群组踢人
 * */
@Getter
public class GroupKickHandler extends AbstractAPIHandler<Object> {

    /**
     * 群号
     * */
    @SerializedName("group_id")
    private Long groupId;

    /**
     * 被踢的 QQ 号
     * */
    @SerializedName("user_id")
    private Long userId;

    /**
     * 拒绝此人的加群请求
     * <p>
     * 默认为 false
     * */
    @SerializedName("reject_add_request")
    private Boolean rejectAddRequest;

    public GroupKickHandler() {
        super("set_group_kick");
    }

    public GroupKickHandler setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public GroupKickHandler setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public GroupKickHandler setRejectAddRequest(Boolean rejectAddRequest) {
        this.rejectAddRequest = rejectAddRequest;
        return this;
    }

}
