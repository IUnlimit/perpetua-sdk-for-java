package com.illtamer.perpetua.sdk.message.driver;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 解决反序列化 number 默认转型成 double 的问题
 * */
public class MapTypeAdapter implements JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return parseJsonValue(json);
    }

    private Map<String, Object> parseJsonObject(JsonObject jsonObject) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            map.put(entry.getKey(), parseJsonValue(entry.getValue()));
        }
        return map;
    }

    private List<Object> parseJsonArray(com.google.gson.JsonArray jsonArray) {
        List<Object> list = new java.util.ArrayList<>();
        for (JsonElement element : jsonArray) {
            list.add(parseJsonValue(element));
        }
        return list;
    }

    private Object parseJsonValue(JsonElement element) {
        if (element.isJsonNull()) {
            return null;
        } else if (element.isJsonObject()) {
            return parseJsonObject(element.getAsJsonObject());
        } else if (element.isJsonArray()) {
            return parseJsonArray(element.getAsJsonArray());
        } else if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                return primitive.getAsString();
            } else if (primitive.isBoolean()) {
                return primitive.getAsBoolean();
            } else if (primitive.isNumber()) {
                String numStr = primitive.getAsString();
                try {
                    BigDecimal bd = new BigDecimal(numStr);
                    if (bd.scale() <= 0) {
                        // 整数
                        if (bd.compareTo(BigDecimal.valueOf(Integer.MIN_VALUE)) >= 0 &&
                                bd.compareTo(BigDecimal.valueOf(Integer.MAX_VALUE)) <= 0) {
                            return bd.intValue();
                        } else if (bd.compareTo(BigDecimal.valueOf(Long.MIN_VALUE)) >= 0 &&
                                bd.compareTo(BigDecimal.valueOf(Long.MAX_VALUE)) <= 0) {
                            return bd.longValue();
                        }
                    }
                    // 小数或超大数
                    return bd.doubleValue();
                } catch (NumberFormatException e) {
                    return primitive.getAsDouble(); // fallback
                }
            }
        }
        return null;
    }

}
