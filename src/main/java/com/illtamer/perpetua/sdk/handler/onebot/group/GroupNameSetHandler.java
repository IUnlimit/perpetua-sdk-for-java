package com.illtamer.perpetua.sdk.handler.onebot.group;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.handler.onebot.AbstractAPIHandler;
import lombok.Getter;

/**
 * 设置群名
 * */
@Getter
public class GroupNameSetHandler extends AbstractAPIHandler<Object> {

    @SerializedName("group_id")
    private Long groupId;

    /**
     * 新群名
     * */
    @SerializedName("group_name")
    private String groupName;

    public GroupNameSetHandler() {
        super("set_group_name");
    }

    public GroupNameSetHandler setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public GroupNameSetHandler setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

}
