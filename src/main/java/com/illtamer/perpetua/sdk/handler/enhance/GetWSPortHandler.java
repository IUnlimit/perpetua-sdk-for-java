package com.illtamer.perpetua.sdk.handler.enhance;

import java.util.Map;

/**
 * 获取分配的 WebSocket 端口
 * */
public class GetWSPortHandler extends AbstractEnhanceAPIHandler<Map<String, Object>> {

    public GetWSPortHandler() {
        super("get_ws_port");
    }

}
