package com.illtamer.perpetua.sdk.handler;

import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.exception.APIInvokeException;
import com.illtamer.perpetua.sdk.websocket.OneBotAPIInvoker;

import java.util.function.Supplier;

public abstract class AbstractWSAPIHandler<T> implements APIHandler<T> {

    private transient final String action;

    public AbstractWSAPIHandler(String action) {
        this.action = action;
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public Response<T> request() {
        Supplier<Response<T>> supplier = OneBotAPIInvoker.postHandler(this);
        Response<T> response = supplier.get();
        if ("failed".equals(response.getStatus()))
            throw new APIInvokeException(response);
        return response;
    }

    /**
     * 异步获取请求结果
     * @apiNote 需使用者自行校验返回数据状态
     * */
    public Supplier<Response<T>> requestAsync() {
        return OneBotAPIInvoker.postHandler(this);
    }

}
