package com.illtamer.perpetua.sdk.event;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.message.Message;
import com.illtamer.perpetua.sdk.message.driver.DateTypeAdapter;
import com.illtamer.perpetua.sdk.message.driver.MapTypeAdapter;
import com.illtamer.perpetua.sdk.message.driver.MessageTypeAdapter;
import com.illtamer.perpetua.sdk.message.driver.ResponseMapDeserializer;
import com.illtamer.perpetua.sdk.util.ClassUtil;
import com.illtamer.perpetua.sdk.websocket.OneBotConnection;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 事件处理器
 * */
public class EventResolver {

    private static final LayerEventTree<Event> root = new LayerEventTree<>(Event.class);

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(new TypeToken<Map<String, Object>>(){}.getType(), new MapTypeAdapter())
            .registerTypeAdapter(Response.class, new ResponseMapDeserializer())
            .registerTypeAdapter(Date.class, new DateTypeAdapter())
            .registerTypeAdapter(Message.class, new MessageTypeAdapter())
            .create();

    static {
        init();
    }

    /**
     * 转换事件
     * */
    public static Event convertEvent(JsonObject json) {
        String postType = json.get(Coordinates.POST_TYPE).getAsString();
        Coordinates.PostType type = Coordinates.PostType.format(postType);
        String secTypeName = type.parseSecType();
        String secType = json.get(secTypeName).getAsString();

        String subType = Optional.ofNullable(json.get(Coordinates.SUB_TYPE)).orElse(new JsonPrimitive("")).getAsString();
        String index = constructIndex(postType, secType, subType);
        final Class<? extends Event> clazz = root.get(index);
        return GSON.fromJson(json, clazz);
    }

    @SuppressWarnings("unchecked")
    private static void init() {
        ClassLoader classLoader = OneBotConnection.class.getClassLoader();
        List<Class<?>> classes = ClassUtil.getClasses("com.illtamer.perpetua.sdk.event", classLoader);
        classes.stream()
                .filter(clazz -> clazz.isAnnotationPresent(Coordinates.class))
                .forEach(clazz -> {
                    Coordinates coordinates = clazz.getAnnotation(Coordinates.class);
                    root.add(coordinates, (Class<? extends Event>) clazz);
                });
    }

    private static String constructIndex(String... args) {
        StringBuilder builder = new StringBuilder();
        for (String arg : args)
            if (arg != null && arg.length() != 0)
                builder.append('.').append(arg);
        builder.deleteCharAt(0);
        return builder.toString();
    }

}
