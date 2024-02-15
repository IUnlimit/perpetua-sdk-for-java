package com.illtamer.perpetua.sdk.event.distributed;

import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.handler.OpenAPIHandling;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 客户端广播事件
 * <p>
 * 当一个客户端通过 API 向其他客户端发送广播传递数据时，接收消息的客户端会收到此事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.DISTRIBUTED,
        secType = "broadcast"
)
public class ClientBroadcastEvent extends DistributedEvent {

    /**
     * 广播的数据
     * */
    private String data;

    /**
     * 发送广播回调
     * @see ClientBroadcastCallbackEvent
     * */
    public void callback(String data) {
        OpenAPIHandling.sendBroadcastDataCallback(data, getUuid(), getClient());
    }

}
