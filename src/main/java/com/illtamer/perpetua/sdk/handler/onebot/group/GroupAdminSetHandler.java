package com.illtamer.perpetua.sdk.handler.onebot.group;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;
import lombok.Getter;

/**
 * 设置群管理员
 * */
@Getter
public class GroupAdminSetHandler extends AbstractWSAPIHandler<Object> {

    @SerializedName("group_id")
    private Long groupId;

    @SerializedName("user_id")
    private Long userId;

    /**
     * true 为设置, false 为取消
     * @apiNote 默认值为 true
     * */
    private Boolean enable;

    public GroupAdminSetHandler() {
        super("set_group_admin");
    }

    public GroupAdminSetHandler setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public GroupAdminSetHandler setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public GroupAdminSetHandler setEnable(Boolean enable) {
        this.enable = enable;
        return this;
    }

}
