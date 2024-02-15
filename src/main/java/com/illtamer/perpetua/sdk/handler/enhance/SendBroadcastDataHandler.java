package com.illtamer.perpetua.sdk.handler.enhance;

import com.illtamer.perpetua.sdk.entity.transfer.entity.Client;
import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 发送客户端广播数据
 * */
@Getter
public class SendBroadcastDataHandler extends AbstractWSAPIHandler<Map<String, Object>> {

    /**
     * 指定的客户端列表
     * @apiNote 不能为空
     * */
    private List<Client> clients;

    /**
     * 需要广播的数据
     * */
    private String data;

    public SendBroadcastDataHandler() {
        super("send_broadcast_data");
        this.clients = new LinkedList<>();
    }

    public SendBroadcastDataHandler addClient(Client client) {
        clients.add(client);
        return this;
    }

    public SendBroadcastDataHandler addClients(List<Client> clients) {
        this.clients.addAll(clients);
        return this;
    }

    public SendBroadcastDataHandler setData(String data) {
        this.data = data;
        return this;
    }

}
