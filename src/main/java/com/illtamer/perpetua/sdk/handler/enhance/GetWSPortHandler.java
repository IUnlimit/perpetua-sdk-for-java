package com.illtamer.perpetua.sdk.handler.enhance;

import com.illtamer.perpetua.sdk.handler.AbstractWebAPIHandler;

import java.util.Map;

/**
 * 获取分配的 WebSocket 端口
 * */
public class GetWSPortHandler extends AbstractWebAPIHandler<Map<String, Object>> {

    public GetWSPortHandler() {
        super("get_ws_port");
    }

}
