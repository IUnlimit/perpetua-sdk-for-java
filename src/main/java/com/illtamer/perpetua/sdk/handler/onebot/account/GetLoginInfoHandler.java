package com.illtamer.perpetua.sdk.handler.onebot.account;

import com.google.gson.Gson;
import com.illtamer.perpetua.sdk.entity.transfer.entity.LoginInfo;
import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;

import java.util.Map;

public class GetLoginInfoHandler extends AbstractWSAPIHandler<Map<String, Object>> {

    public GetLoginInfoHandler() {
        super("get_login_info");
    }

    public static LoginInfo parse(Map<String, Object> response) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(response), LoginInfo.class);
    }

}
