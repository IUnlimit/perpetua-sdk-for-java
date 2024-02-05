package com.illtamer.perpetua.sdk.event.meta;

import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.entity.transfer.entity.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 心跳事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.META_EVENT,
        secType = "heartbeat"
)
public class HeartbeatEvent extends MetaEvent {

    /**
     * 状态信息
     * @apiNote status 字段的内容和 get_status 接口的快速操作相同。
     * */
    private Status status;

    /**
     * 到下次心跳的间隔，单位毫秒
     * */
    private Long interval;

}
