package com.illtamer.perpetua.sdk.handler;

import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.exception.APIInvokeException;
import com.illtamer.perpetua.sdk.websocket.OneBotConnection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * API 逻辑接口
 * @apiNote <a href="https://docs.go-cqhttp.org/api/#">...</a>
 * */
public interface APIHandler<T> {

    Map<String, String> HEADERS = new HashMap<>(Collections.singletonMap("Authorization", OneBotConnection.getAuthorization()));

    /**
     * 获取行为标识
     * @apiNote 若为 WebAPI 调用则返回终结点坐标（URI）、若为 WebSocket 调用则返回 action 字段
     * */
    String getAction();

    /**
     * @throws APIInvokeException API 调用失败异常
     * */
    Response<T> request();

}
