package com.illtamer.perpetua.sdk.message;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.illtamer.perpetua.sdk.Pair;
import com.illtamer.perpetua.sdk.entity.TransferEntity;
import com.illtamer.perpetua.sdk.exception.ExclusiveMessageException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Json 类型消息
 * <p>
 * 用于主动传输消息
 * */
public class JsonMessage extends Message {

    private final JsonArray array;
    private final MessageChain messageChain;

    private boolean textOnly;

    protected JsonMessage() {
        this(new JsonArray(), true);
    }

    private JsonMessage(JsonArray array, boolean textOnly) {
        this.array = array;
        this.textOnly = textOnly;
        this.messageChain = new MessageChain();
    }

    @Override
    @NotNull
    public MessageChain getMessageChain() {
        return messageChain;
    }

    @Override
    @NotNull
    public List<String> getCleanMessage() {
        List<String> list = new ArrayList<>();
        for (JsonElement element : array) {
            JsonObject object = (JsonObject) element;
            if (!"text".equals(object.get("type").getAsString())) continue;
            list.add(object.get("data").getAsJsonObject().get("text").getAsString());
        }
        return list;
    }

    @Override
    public int getSize() {
        return array.size();
    }

    @Override
    public boolean isTextOnly() {
        return textOnly;
    }

    @Override
    public JsonMessage clone() {
        return new JsonMessage(array.deepCopy(), textOnly);
    }

    @Override
    public String toString() {
        return array.toString();
    }

    @Override
    protected void add(String type, Map<String, @Nullable Object> data) {
        if (textOnly)
            textOnly = "text".equals(type);
        JsonObject dataJson = new JsonObject();
        final List<Map.Entry<String, Object>> notnull = data.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toList());
        // notnull map can be empty
        messageChain.boltOn(type, notnull);

        notnull.forEach(entry -> {
                    Object value = entry.getValue();
                    JsonElement element;
                    if (value instanceof Message)
                        element = ((JsonMessage) value).array;
                    else
                        element = new JsonPrimitive(value.toString());
                    dataJson.add(entry.getKey(), element);
                });
        JsonObject node = new JsonObject();
        node.add("type", new JsonPrimitive(type));
        node.add("data", dataJson);
        array.add(node);
    }

    @Override
    protected void removeIf(Predicate<TransferEntity> predicate) {
        messageChain.removeWith(predicate, array::remove);
    }

    @Override
    protected void addExclusive(String type, Map<String, @Nullable Object> data) {
        if (!array.isEmpty()) {
            for (JsonElement jsonElement : array) {
                JsonObject object = (JsonObject) jsonElement;
                String typeStr = object.get("type").getAsString();
                // 不检查合并消息
                if ("node".equals(typeStr)) continue;
                throw new ExclusiveMessageException(type);
            }
        }
        add(type, data);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List<Pair<String, Map<String, @NotNull Object>>> entryList() {
        final Gson gson = new Gson();
        List<Pair<String, Map<String, @NotNull Object>>> list = new ArrayList<>(array.size());
        for (JsonElement element : array) {
            JsonObject node = (JsonObject) element;
            final String type = node.get("type").getAsString();
            final Map<String, Object> data = gson.fromJson(node.get("data"), Map.class);
            list.add(new Pair<>(type, data));
        }
        return list;
    }

    /**
     * json 反序列化
     * @apiNote 完整经历创建过程，自动校验参数
     * */
    public static JsonMessage deserialize(JsonArray array) {
        JsonMessage message = new JsonMessage();
        for (JsonElement e : array) {
            JsonObject object = (JsonObject) e;
            String type = object.get("type").getAsString();
            JsonObject data = object.get("data").getAsJsonObject();
            Map<String, Object> map = new LinkedTreeMap<>();
            map.putAll(data.asMap());
            message.add(type, map);
        }
        return message;
    }

}
