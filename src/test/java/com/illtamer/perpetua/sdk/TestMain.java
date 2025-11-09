package com.illtamer.perpetua.sdk;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.illtamer.perpetua.sdk.entity.transfer.entity.Client;
import com.illtamer.perpetua.sdk.event.Event;
import com.illtamer.perpetua.sdk.event.EventResolver;
import com.illtamer.perpetua.sdk.event.distributed.ClientBroadcastEvent;
import com.illtamer.perpetua.sdk.event.distributed.DistributedEvent;
import com.illtamer.perpetua.sdk.event.message.MessageEvent;
import com.illtamer.perpetua.sdk.event.meta.HeartbeatEvent;
import com.illtamer.perpetua.sdk.handler.OpenAPIHandling;
import com.illtamer.perpetua.sdk.handler.enhance.GetOnlineClientsHandler;
import com.illtamer.perpetua.sdk.websocket.OneBotConnection;

import java.util.Map;

public class TestMain {

    public static void main(String[] args) throws Exception {
        testGsonPraseInt();
    }

    private static void testGsonPraseInt() throws Exception {
        Response response = EventResolver.GSON.fromJson("{\"status\":\"ok\",\"retcode\":0,\"data\":{\"port\":36831}}", Response.class);
        System.out.println(response);
    }

    private static void testEnhanceAPI() {
        OneBotConnection.setEnhanceWebAPIUrl(String.format("http://%s:%d", "localhost", 8080));
        Response<Map<String, Object>> response = new GetOnlineClientsHandler().request();
        System.out.println(GetOnlineClientsHandler.parse(response));
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
//            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
//                Response<?> response = new TestHandler("send_like")
//                        .request();
//            Client client = new Client();
//                client.setClientName("client0");
//            System.out.println(OpenAPIHandling.sendBroadcastData("==安师大上的=d", client));
            OpenAPIHandling.setClientName("client0");
//            }
            //            OneBotConnection.getChannel().writeAndFlush(new TextWebSocketFrame("{\"action\":\"send_private_msg\",\"params\":{\"user_id\":765743073,\"message\":\"你好\"}}"));
        }).start();
        OneBotConnection.start("127.0.0.1", 8080, null, event -> {
            if (event instanceof HeartbeatEvent) return;
            if (event instanceof ClientBroadcastEvent) {
                ((ClientBroadcastEvent) event).callback("回调！");
            }
        });
    }

}
