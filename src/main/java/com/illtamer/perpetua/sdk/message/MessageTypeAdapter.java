package com.illtamer.perpetua.sdk.message;

import com.google.gson.*;
import com.illtamer.perpetua.sdk.util.Assert;
import lombok.extern.java.Log;

import java.lang.reflect.Type;

/**
 * Gson {@link Message} TypeAdapter
 * */
@Log
public class MessageTypeAdapter implements JsonSerializer<Message>, JsonDeserializer<Message> {

    @Override
    public Message deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json instanceof JsonArray) {
            // json message
            return JsonMessage.deserialize((JsonArray) json);
        } else {
            // cq code
            return CQMessage.deserialize(json.getAsString());
        }
    }

    @Override
    public JsonElement serialize(Message src, Type typeOfSrc, JsonSerializationContext context) {
        Assert.isTrue(src instanceof CQMessage, "Unsupported type");
        return new JsonPrimitive(src.toString());
    }

}
