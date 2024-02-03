package com.illtamer.perpetua.sdk.handler;

import com.google.gson.Gson;
import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.entity.transfer.receive.BotStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * 机器人状态查看
 * */
public class StatusGetHandler extends AbstractAPIHandler<Map<String, Object>> {

    public StatusGetHandler() {
        super("/get_status");
    }

    @NotNull
    public static BotStatus parse(@NotNull Response<Map<String, Object>> response) {
        final Gson gson = new Gson();
        return gson.fromJson(gson.toJson(response.getData()), BotStatus.class);
    }

}
