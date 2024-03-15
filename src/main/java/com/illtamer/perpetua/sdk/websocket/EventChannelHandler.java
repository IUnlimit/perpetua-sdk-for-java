package com.illtamer.perpetua.sdk.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.illtamer.perpetua.sdk.event.Event;
import com.illtamer.perpetua.sdk.event.EventResolver;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.logging.Level;

@Log
class EventChannelHandler extends SimpleChannelInboundHandler<Object> {

    private final ExecutorService waitCallbackEventThreadPool;
    private final WebSocketClientHandshaker handshaker;
    private final Consumer<Event> eventConsumer;
    @Getter
    private ChannelPromise handshakeFuture;

    public EventChannelHandler(ExecutorService eventThreadPool, WebSocketClientHandshaker handshaker, Consumer<Event> eventConsumer) {
        this.waitCallbackEventThreadPool = eventThreadPool;
        this.handshaker = handshaker;
        this.eventConsumer = eventConsumer;
    }

    /**
     * event-thread
     *  - echo event (thread1)
     *  - common event (thread2)
     * */
    protected void handleMessage(String message) {
        JsonObject json = new Gson().fromJson(message, JsonObject.class);
        if (json.get("echo") != null) {
            OneBotConnection.callbackThreadPool.execute(() ->
                    OneBotAPIInvoker.callback(json));
            return;
        }

        Event event = EventResolver.convertEvent(json);
        try {
            waitCallbackEventThreadPool.execute(() -> eventConsumer.accept(event));
        } catch (Exception e) {
            log.log(Level.SEVERE, "调用事件出错", e);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        handshaker.handshake(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        Channel ch = ctx.channel();
        // 握手协议返回，设置结束握手
        if (!handshaker.isHandshakeComplete()) {
            try {
                handshaker.finishHandshake(ch, (FullHttpResponse) msg);
                log.info("WebSocket Client connected!");
                handshakeFuture.setSuccess();
            } catch (WebSocketHandshakeException e) {
                log.severe("WebSocket Client failed to connect");
                handshakeFuture.setFailure(e);
            }
            return;
        }

        if (msg instanceof TextWebSocketFrame) {
            handleMessage(((TextWebSocketFrame) msg).text());
        } else if (msg instanceof PongWebSocketFrame) {
            System.out.println("WebSocket Client received pong");
        } else if (msg instanceof CloseWebSocketFrame){
            log.info("WebSocket Client received closing");
            ch.close();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("WebSocket Client disconnected!");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.log(Level.SEVERE, "Exception occurred: ", cause);
        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }
        ctx.close();
    }

}
