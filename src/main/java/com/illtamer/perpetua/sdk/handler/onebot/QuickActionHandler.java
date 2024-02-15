package com.illtamer.perpetua.sdk.handler.onebot;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.illtamer.perpetua.sdk.event.Event;
import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;
import com.illtamer.perpetua.sdk.message.Message;
import lombok.Getter;

import java.util.Map;

/**
 * 快速操作 APIHandler
 * */
@Getter
@Deprecated
public class QuickActionHandler extends AbstractWSAPIHandler<Map<String, Object>> {

    /**
     * 事件数据对象
     * <p>
     * 可做精简, 如去掉 message 等无用字段
     * */
    private final Event context;

    /**
     * 快速操作对象
     * <p>
     * 例如 {"ban": true, "reply": "请不要说脏话"}
     * */
    private final JsonObject operation;

    public QuickActionHandler(Event context) {
        super(".handle_quick_operation");
        this.context = context;
        this.operation = new JsonObject();
    }

    public QuickActionHandler addOperation(String key, String value) {
        this.operation.addProperty(key, value);
        return this;
    }

    public QuickActionHandler addOperation(String key, Boolean value) {
        this.operation.addProperty(key, value);
        return this;
    }

    public QuickActionHandler addOperation(String key, Number value) {
        this.operation.addProperty(key, value);
        return this;
    }

    public QuickActionHandler addOperation(String key, Message value) {
        this.operation.add(key, new Gson().fromJson(value.toString(), JsonArray.class));
        return this;
    }

}
