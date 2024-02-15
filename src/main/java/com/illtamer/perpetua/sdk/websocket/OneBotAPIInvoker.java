package com.illtamer.perpetua.sdk.websocket;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.event.EventResolver;
import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;
import com.illtamer.perpetua.sdk.util.Assert;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class OneBotAPIInvoker {

    private static final Cache<String, CompletableFuture<Response<?>>> localCache = CacheBuilder.newBuilder()
            .initialCapacity(64)
            .maximumSize(1024)
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .build();

    @SuppressWarnings("unchecked")
    public static <T> Supplier<Response<T>> postHandler(AbstractWSAPIHandler<T> handler) {
        Assert.notNull(OneBotConnection.channel, "WebSocket 连接未开启");

        Gson gson = EventResolver.GSON;
        String uuid = UUID.randomUUID().toString();
        JsonObject params = (JsonObject) gson.toJsonTree(handler);
        JsonObject sendObj = new JsonObject();
        sendObj.add("action", new JsonPrimitive(handler.getAction()));
        sendObj.add("params", params);
        sendObj.add("echo", new JsonPrimitive(uuid));

        CompletableFuture<Response<?>> future = new CompletableFuture<>();
        localCache.put(uuid, future);
        OneBotConnection.channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(sendObj)));

        return () -> {
            try {
                return (Response<T>) future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        };
    }

    // OneBot API callback
    // @param json {"data":{"message_id":0},"echo":"","retcode":0,"status":"ok"}
    protected static void callback(JsonObject json) {
        Response<?> resp = EventResolver.GSON.fromJson(json, Response.class);
        Optional.ofNullable(localCache.getIfPresent(resp.getEcho())).ifPresent(
                future -> {
                    localCache.invalidate(resp.getEcho());
                    future.complete(resp);
                }
        );
    }

}
