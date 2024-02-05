package com.illtamer.perpetua.sdk.handler.enhance;

import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.event.EventResolver;
import com.illtamer.perpetua.sdk.exception.APIInvokeException;
import com.illtamer.perpetua.sdk.handler.APIHandler;
import com.illtamer.perpetua.sdk.util.HttpRequestUtil;
import com.illtamer.perpetua.sdk.websocket.OneBotConnection;

/**
 * @param <T> 返回的数据类型 {@link Response#getData()}
 * */
public abstract class AbstractEnhanceAPIHandler<T> implements APIHandler<T> {

    private static final String API_PREFIX = "/api/";

    private transient final String endpoint;

    public AbstractEnhanceAPIHandler(String action) {
        this.endpoint = API_PREFIX + action;
    }

    @Override
    public String getAction() {
        return endpoint;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Response<T> request() {
        String json = HttpRequestUtil.request(OneBotConnection.getEnhanceWebAPIUrl() + endpoint, this, HEADERS);
        Response<T> response = EventResolver.GSON.fromJson(json, Response.class);
        if ("failed".equals(response.getStatus()))
            throw new APIInvokeException(response);
        return response;
    }

}
