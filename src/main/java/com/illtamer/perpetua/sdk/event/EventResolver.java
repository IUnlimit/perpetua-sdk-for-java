package com.illtamer.perpetua.sdk.event;

import com.google.gson.*;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.message.DateTypeAdapter;
import com.illtamer.perpetua.sdk.message.MapTypeAdapter;
import com.illtamer.perpetua.sdk.message.Message;
import com.illtamer.perpetua.sdk.message.MessageTypeAdapter;
import com.illtamer.perpetua.sdk.util.ClassUtil;
import com.illtamer.perpetua.sdk.websocket.OneBotConnection;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 事件处理器
 * */
public class EventResolver {

    private static final LayerEventTree<Event> root = new LayerEventTree<>(Event.class);

    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateTypeAdapter())
            .registerTypeAdapter(Message.class, new MessageTypeAdapter())
            .create();

    static {
        init();
        injectGson();
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

    @SuppressWarnings("unchecked")
    private static void injectGson() {
        try {
            Field factories = Gson.class.getDeclaredField("factories");
            factories.setAccessible(true);
            Object o = factories.get(GSON);
            Class<?>[] declaredClasses = Collections.class.getDeclaredClasses();
            for (Class<?> c : declaredClasses) {
                if ("java.util.Collections$UnmodifiableList".equals(c.getName())) {
                    Field listField = c.getDeclaredField("list");
                    listField.setAccessible(true);
                    List<TypeAdapterFactory> list = (List<TypeAdapterFactory>) listField.get(o);
                    int i = list.indexOf(ObjectTypeAdapter.getFactory(ToNumberPolicy.DOUBLE));
                    list.set(i, new TypeAdapterFactory() {
                        @SuppressWarnings("unchecked")
                        @Override
                        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
                            if (type.getRawType() == Object.class) {
                                return (TypeAdapter<T>) new MapTypeAdapter();
                            }
                            return null;
                        }
                    });
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
