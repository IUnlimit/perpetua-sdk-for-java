package com.illtamer.perpetua.sdk.event.distributed;

import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.entity.transfer.entity.Client;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 客户端广播回调事件
 * <p>
 * 当客户端接收到 客户端广播 事件时，可通过 API 向广播发起方回调事件，发起方接收到的事件定义如下
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.DISTRIBUTED,
        secType = "broadcast_callback"
)
public class ClientBroadcastCallbackEvent extends DistributedEvent {

    /**
     * 回调的数据
     * */
    private String data;

}
