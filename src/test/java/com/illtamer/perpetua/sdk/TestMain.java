package com.illtamer.perpetua.sdk;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.illtamer.perpetua.sdk.event.Event;
import com.illtamer.perpetua.sdk.event.EventResolver;
import com.illtamer.perpetua.sdk.event.message.MessageEvent;
import com.illtamer.perpetua.sdk.event.meta.HeartbeatEvent;
import com.illtamer.perpetua.sdk.handler.OpenAPIHandling;
import com.illtamer.perpetua.sdk.handler.enhance.GetWSPortHandler;
import com.illtamer.perpetua.sdk.message.Message;
import com.illtamer.perpetua.sdk.message.MessageBuilder;
import com.illtamer.perpetua.sdk.websocket.OneBotConnection;

import java.util.Map;
import java.util.Scanner;

public class TestMain {

    public static void main(String[] args) throws Exception {
        testConnect();
    }

    private static void testEnhanceAPI() {
        OneBotConnection.setEnhanceWebAPIUrl(String.format("http://%s:%d", "localhost", 8080));
        Response<Map<String, Object>> response = new GetWSPortHandler().request();
        System.out.println((Integer) response.getData().get("port"));
    }

    private static void testParse() {
        String msg = "{\"font\":0,\"message\":[{\"data\":{\"text\":\"你好\"},\"type\":\"text\"}],\"message_id\":3248073960,\"message_type\":\"private\",\"post_type\":\"message\",\"raw_message\":\"你好\",\"self_id\":3012218237,\"sender\":{},\"sub_type\":\"friend\",\"time\":1707018752,\"user_id\":765743073}";
        Event event = EventResolver.convertEvent(new Gson()
                .fromJson(msg, JsonObject.class));
        System.out.println(event);
    }

    private static void testConnect() throws Exception {
        long groupId = 863522624L;
        long userId = 765743073L;
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
//                Response<?> response = new TestHandler("send_like")
//                        .request();
                System.out.println(OpenAPIHandling.getGroups());
            }
            //            OneBotConnection.getChannel().writeAndFlush(new TextWebSocketFrame("{\"action\":\"send_private_msg\",\"params\":{\"user_id\":765743073,\"message\":\"你好\"}}"));
        }).start();
        OneBotConnection.start("127.0.0.1", 8080, null, event -> {
            if (event instanceof HeartbeatEvent) return;
            if (event instanceof MessageEvent) {
                System.out.println(event);
                System.out.println(((MessageEvent) event).getMessage().getMessageChain());


            }
        });
    }

}
