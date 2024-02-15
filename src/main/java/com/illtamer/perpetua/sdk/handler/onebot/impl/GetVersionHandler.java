package com.illtamer.perpetua.sdk.handler.onebot.impl;

import com.google.gson.Gson;
import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.entity.transfer.entity.VersionInfo;
import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * 获取 OneBot 实现的版本信息
 * */
public class GetVersionHandler extends AbstractWSAPIHandler<Map<String, Object>> {

    public GetVersionHandler() {
        super("get_version_info");
    }

    @NotNull
    public static VersionInfo parse(@NotNull Response<Map<String, Object>> response) {
        final Gson gson = new Gson();
        return gson.fromJson(gson.toJson(response.getData()), VersionInfo.class);
    }

}
