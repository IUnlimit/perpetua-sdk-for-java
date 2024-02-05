package com.illtamer.perpetua.sdk.event.notice;

import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.entity.transfer.entity.Device;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 其他客户端在线状态变更事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.NOTICE,
        secType = "client_status"
)
@Deprecated
public class ClientStatusChangeEvent extends NoticeEvent {

    /**
     * 客户端信息
     * */
    private Device client;

    /**
     * 当前是否在线
     * */
    private Boolean online;

}
