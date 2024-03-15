package com.illtamer.perpetua.sdk.websocket;

import com.illtamer.perpetua.sdk.entity.transfer.entity.LoginInfo;
import com.illtamer.perpetua.sdk.event.Event;
import com.illtamer.perpetua.sdk.handler.OpenAPIHandling;
import com.illtamer.perpetua.sdk.handler.enhance.GetWSPortHandler;
import com.illtamer.perpetua.sdk.util.Assert;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * onebot WebSocket 连接初始化类
 * */
@Log
public class OneBotConnection {

    // 事件回调线程池，尽量多个，IO 密集型
    @Setter
    protected static ExecutorService callbackThreadPool = Executors.newFixedThreadPool(10);
    // 事件监听线程池，一个 ws 连接对应至少一个
    @Setter
    protected static ExecutorService eventThreadPool = Executors.newFixedThreadPool(2);

    /**
     * 拓展 WebAPI 请求地址
     * */
    @Setter
    @Getter
    private static String enhanceWebAPIUrl;

    /**
     * 验证 TOKEN
     * */
    @Setter
    @Getter
    private static String authorization;

    /**
     * 登录信息
     * */
    @Getter
    private static LoginInfo loginInfo;

    /**
     * WebSocket 连接状态
     * */
    @Getter
    private static boolean running = false;

    protected static Channel channel;

    /**
     * 启动 ws 连接
     * @param ip perpetua 所在 ip
     * @param apiPort webapi 开放端口
     * @param authorization 验证 token，可为空
     * @param eventConsumer 事件消费者
     * */
    public static void start(@NotNull String ip, @NotNull Integer apiPort, @Nullable String authorization, Consumer<Event> eventConsumer) throws InterruptedException {
        Assert.notNull(ip, "ip can not be null");
        Assert.notNull(apiPort, "wsUri can not be null");

        OneBotConnection.enhanceWebAPIUrl = String.format("http://%s:%d", ip, apiPort);
        OneBotConnection.authorization = authorization;

        Integer wsPort = (Integer) new GetWSPortHandler().request().getData().get("port");
        String wsUri = String.format("ws://%s:%d", ip, wsPort);

        try {
            connect(wsUri, eventConsumer, OpenAPIHandling::getLoginInfo);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static void connect(String wsUri, Consumer<Event> eventConsumer, Supplier<LoginInfo> checkAPI) throws URISyntaxException, InterruptedException {
        URI uri = new URI(wsUri);
        HttpHeaders httpHeaders = new DefaultHttpHeaders().set("Content-Type", "application/json");
        if (authorization != null && !authorization.isEmpty()) {
            httpHeaders.set("Authorization", authorization);
        }
        EventChannelHandler eventHandler = new EventChannelHandler(
                eventThreadPool,
                WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null, true, httpHeaders),
                eventConsumer);
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline()
                                    .addLast(new HttpClientCodec())
                                    // 添加一个用于支持大数据流的支持
                                    .addLast(new ChunkedWriteHandler())
                                    // 添加一个聚合器，这个聚合器主要是将HttpMessage聚合成FullHttpRequest/Response
                                    .addLast(new HttpObjectAggregator(1024 * 64))
                                    .addLast(WebSocketClientCompressionHandler.INSTANCE)
                                    .addLast(eventHandler);
                        }
                    });
            channel = bootstrap
                    .connect(uri.getHost(), uri.getPort())
                    .sync()
                    .channel();
            // 阻塞等待是否握手成功
            eventHandler.getHandshakeFuture().sync();
            loginInfo = checkAPI.get();
            running = true;
            log.info("Onebot websocket 握手成功");
            channel.closeFuture().sync();
            running = false;
        } finally {
            group.shutdownGracefully();
        }
    }

}
