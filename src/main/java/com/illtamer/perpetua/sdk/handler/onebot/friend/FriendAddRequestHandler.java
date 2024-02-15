package com.illtamer.perpetua.sdk.handler.onebot.friend;

import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;
import lombok.Getter;

/**
 * 处理加好友请求
 * */
@Getter
public class FriendAddRequestHandler extends AbstractWSAPIHandler<Object> {

    /**
     * 加好友请求的 flag（需从上报的数据中获得）
     * */
    private String flag;

    /**
     * 是否同意请求
     * */
    private Boolean approve;

    /**
     * 添加后的好友备注（仅在同意时有效）
     * */
    private String remark;

    public FriendAddRequestHandler() {
        super("set_friend_add_request");
    }

    public FriendAddRequestHandler setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public FriendAddRequestHandler setApprove(Boolean approve) {
        this.approve = approve;
        return this;
    }

    public FriendAddRequestHandler setRemark(String remark) {
        this.remark = remark;
        return this;
    }

}
