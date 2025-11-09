package com.illtamer.perpetua.sdk.handler;

import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.exception.APIInvokeException;
import com.illtamer.perpetua.sdk.websocket.OneBotAPIInvoker;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
        CompletableFuture<Response<T>> future = OneBotAPIInvoker.postHandlerFuture(this);
        try {
            // todo timeout
            Response<T> response = future.get();
            if ("failed".equals(response.getStatus()))
                throw new APIInvokeException(response);
            return response;
        } catch (ExecutionException | InterruptedException e) {
            throw new APIInvokeException(e);
        }
    }

    /**
     * 异步获取请求结果
     * @apiNote 需使用者自行校验返回数据状态
     * */
    public CompletableFuture<Response<T>> requestAsync() {
        return OneBotAPIInvoker.postHandlerFuture(this);
    }

}
