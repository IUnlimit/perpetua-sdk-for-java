package com.illtamer.perpetua.sdk.message.driver;

import com.google.gson.*;
import com.illtamer.perpetua.sdk.Response;

import java.lang.reflect.Type;
import java.util.Map;

public class ResponseMapDeserializer implements JsonDeserializer<Response<Map<String, Object>>> {

    private final MapTypeAdapter mapTypeAdapter = new MapTypeAdapter();

    @Override
    public Response<Map<String, Object>> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String status = getString(jsonObject, "status");
        Integer retcode = getInteger(jsonObject, "retcode");
        String msg = getString(jsonObject, "msg");
        String wording = getString(jsonObject, "wording");
        String echo = getString(jsonObject, "echo");

        // 使用自定义逻辑解析 data 字段
        Map<String, Object> data = null;
        if (jsonObject.has("data") && !jsonObject.get("data").isJsonNull()) {
            JsonElement dataElement = jsonObject.get("data");
            data = mapTypeAdapter.deserialize(dataElement, Map.class, context);
        }
        return new Response<>(status, retcode, msg, wording, data, echo);
    }

    private String getString(JsonObject obj, String key) {
        return obj.has(key) && !obj.get(key).isJsonNull() ? obj.get(key).getAsString() : null;
    }

    private Integer getInteger(JsonObject obj, String key) {
        return obj.has(key) && !obj.get(key).isJsonNull() ? obj.get(key).getAsInt() : null;
    }
}