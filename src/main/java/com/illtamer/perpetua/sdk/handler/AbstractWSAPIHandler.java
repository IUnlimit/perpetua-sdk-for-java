package com.illtamer.perpetua.sdk.handler;

import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.exception.APIInvokeException;
import com.illtamer.perpetua.sdk.websocket.OneBotAPIInvoker;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
            Response<T> response = future.get(10, TimeUnit.SECONDS);
            if ("failed".equals(response.getStatus()))
                throw new APIInvokeException(response);
            return response;
        } catch (ExecutionException | InterruptedException e) {
            throw new APIInvokeException(e);
        } catch (TimeoutException e) {
            future.cancel(true);
            throw new APIInvokeException(new RuntimeException("WebSocket API 请求超时: " + action, e));
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
