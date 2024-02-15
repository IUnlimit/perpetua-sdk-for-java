package com.illtamer.perpetua.sdk.handler.onebot.group;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;
import lombok.Getter;

/**
 * 群组单人禁言 APIHandler
 * */
@Getter
public class GroupBanAPIHandler extends AbstractWSAPIHandler<Object> {

    /**
     * 群号
     * */
    @SerializedName("group_id")
    private Long groupId;

    /**
     * 要禁言的 QQ 号
     * */
    @SerializedName("user_id")
    private Long userId;

    /**
     * 禁言时长
     * <p>
     * 单位秒, 0 表示取消禁言
     * 默认 30 * 60
     * */
    private Integer duration;

    public GroupBanAPIHandler() {
        super("set_group_ban");
    }

    public GroupBanAPIHandler setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public GroupBanAPIHandler setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public GroupBanAPIHandler setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

}
