package com.illtamer.perpetua.sdk.handler.onebot.group;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.handler.onebot.AbstractAPIHandler;
import lombok.Getter;

/**
 * 群全员禁言
 * */
@Getter
public class GroupWholeBanHandler extends AbstractAPIHandler<Object> {

    /**
     * 群号
     * */
    @SerializedName("group_id")
    private Long groupId;

    /**
     * 是否禁言
     * */
    private Boolean enable;

    public GroupWholeBanHandler() {
        super("set_group_whole_ban");
    }

    public GroupWholeBanHandler setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public GroupWholeBanHandler setEnable(Boolean enable) {
        this.enable = enable;
        return this;
    }

}
