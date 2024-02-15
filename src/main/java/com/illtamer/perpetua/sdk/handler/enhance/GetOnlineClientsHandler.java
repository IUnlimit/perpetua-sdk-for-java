package com.illtamer.perpetua.sdk.handler.enhance;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.entity.transfer.entity.Client;
import com.illtamer.perpetua.sdk.handler.AbstractWebAPIHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取当前在线客户端列表
 * */
public class GetOnlineClientsHandler extends AbstractWebAPIHandler<Map<String, Object>> {

    public GetOnlineClientsHandler() {
        super("get_online_clients");
    }

    public static List<Client> parse(@NotNull Response<Map<String, Object>> response) {
        final Gson gson = new Gson();
        final JsonArray array = gson.fromJson(gson.toJson(response.getData()), JsonArray.class);
        List<Client> clients = new ArrayList<>(array.size());
        array.forEach(object -> clients.add(gson.fromJson(object, Client.class)));
        return clients;
    }

}
