package com.illtamer.perpetua.sdk.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.illtamer.perpetua.sdk.event.Event;
import com.illtamer.perpetua.sdk.event.EventResolver;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.util.function.Consumer;
import java.util.logging.Level;

@Log
class EventHandler extends SimpleChannelInboundHandler<Object> {

    private final Consumer<Event> eventConsumer;

    @Setter
    private WebSocketClientHandshaker handshake;

    @Getter
    private ChannelPromise handshakeFuture;

    public EventHandler(Consumer<Event> eventConsumer) {
        this.eventConsumer = eventConsumer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.handshakeFuture = ctx.newPromise();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        // 握手协议返回，设置结束握手
        if (!this.handshake.isHandshakeComplete()){
            FullHttpResponse response = (FullHttpResponse) msg;
            this.handshake.finishHandshake(ctx.channel(), response);
            this.handshakeFuture.setSuccess();
        } else if (msg instanceof TextWebSocketFrame) {
            Event event = EventResolver.dispatchEvent(new Gson()
                    .fromJson(((TextWebSocketFrame) msg).text(), JsonObject.class));
            try {
                eventConsumer.accept(event);
            } catch (Exception e) {
                log.severe("调用事件出错");
                e.printStackTrace();
            }
        } else if (msg instanceof CloseWebSocketFrame){
            log.info("channelRead0 CloseWebSocketFrame");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.log(Level.SEVERE, "Error", cause);
        ctx.close();
    }

}
