package com.illtamer.perpetua.sdk.handler.enhance;

import com.illtamer.perpetua.sdk.entity.transfer.entity.Client;
import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;
import lombok.Getter;

/**
 * 发送客户端广播数据回调
 * */
@Getter
public class SendBroadcastDataCallbackHandler extends AbstractWSAPIHandler<Object> {

    /**
     * 指定的客户端
     * @apiNote 不能为空
     * */
    private Client client;

    /**
     * 此次客户端广播事件的唯一id，从事件中获取
     * */
    private String uuid;

    /**
     * 需要广播的数据
     * */
    private String data;

    public SendBroadcastDataCallbackHandler() {
        super("send_broadcast_data_callback");
    }

    public SendBroadcastDataCallbackHandler setClient(Client client) {
        this.client = client;
        return this;
    }

    public SendBroadcastDataCallbackHandler setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public SendBroadcastDataCallbackHandler setData(String data) {
        this.data = data;
        return this;
    }

}
