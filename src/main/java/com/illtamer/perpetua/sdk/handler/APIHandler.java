package com.illtamer.perpetua.sdk.handler;

import com.google.gson.Gson;
import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.config.CQHttpWebSocketConfiguration;
import com.illtamer.perpetua.sdk.exception.APIInvokeException;
import com.illtamer.perpetua.sdk.util.HttpRequestUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * API 逻辑接口
 * @apiNote https://docs.go-cqhttp.org/api/#
 * */
public interface APIHandler<T> {

    Map<String, String> HEADERS = new HashMap<>(Collections.singletonMap("Authorization", CQHttpWebSocketConfiguration.getAuthorization()));

    /**
     * 获取终结点坐标
     * */
    String getEndpoint();

    /**
     * @throws APIInvokeException API 调用失败异常
     * */
    @SuppressWarnings("unchecked")
    default Response<T> request() {
        String json = HttpRequestUtil.postJson(CQHttpWebSocketConfiguration.getHttpUri() + getEndpoint(), this, HEADERS);
        Response<T> response = new Gson().fromJson(json, Response.class);
        if ("failed".equals(response.getStatus()))
            throw new APIInvokeException(response);
        return response;
    }

}
