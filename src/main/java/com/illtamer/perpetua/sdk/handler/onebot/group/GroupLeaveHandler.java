package com.illtamer.perpetua.sdk.handler.onebot.group;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.handler.onebot.AbstractAPIHandler;
import lombok.Getter;

/**
 * 退出群组
 * */
@Getter
public class GroupLeaveHandler extends AbstractAPIHandler<Object> {

    @SerializedName("group_id")
    private Long groupId;

    /**
     * 是否解散
     * <p>
     * 如果登录号是群主, 则仅在此项为 true 时能够解散
     * 默认值为 false
     * */
    @SerializedName("is_dismiss")
    private Boolean isDismiss;

    public GroupLeaveHandler() {
        super("set_group_leave");
    }

    public GroupLeaveHandler setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public GroupLeaveHandler setDismiss(Boolean dismiss) {
        isDismiss = dismiss;
        return this;
    }

}
