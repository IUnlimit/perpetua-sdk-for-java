package com.illtamer.perpetua.sdk.handler.onebot.impl;

import com.google.gson.Gson;
import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.entity.transfer.entity.Status;
import com.illtamer.perpetua.sdk.handler.onebot.AbstractAPIHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * 机器人状态查看
 * */
@Deprecated
public class GetStatusHandler extends AbstractAPIHandler<Map<String, Object>> {

    public GetStatusHandler() {
        super("get_status");
    }

    @NotNull
    public static Status parse(@NotNull Response<Map<String, Object>> response) {
        final Gson gson = new Gson();
        return gson.fromJson(gson.toJson(response.getData()), Status.class);
    }

}
