package com.illtamer.perpetua.sdk.handler.onebot.group;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.handler.onebot.AbstractAPIHandler;
import lombok.Getter;

/**
 * 处理加群请求／邀请
 * */
@Getter
public class GroupAddRequestHandler extends AbstractAPIHandler<Object> {

    /**
     * 加群请求的 flag（需从上报的数据中获得）
     * */
    private String flag;

    /**
     * add 或 invite，请求类型（需要和上报消息中的 sub_type 字段相符）
     * */
    @SerializedName("sub_type")
    private String subType;

    /**
     * 是否同意请求
     * */
    private Boolean approve;

    /**
     * 拒绝理由（仅在拒绝时有效）
     * */
    private String reason;

    public GroupAddRequestHandler() {
        super("set_friend_add_request");
    }

    public GroupAddRequestHandler setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public GroupAddRequestHandler setSubType(String subType) {
        this.subType = subType;
        return this;
    }

    public GroupAddRequestHandler setApprove(Boolean approve) {
        this.approve = approve;
        return this;
    }

    public GroupAddRequestHandler setReason(String reason) {
        this.reason = reason;
        return this;
    }

}
