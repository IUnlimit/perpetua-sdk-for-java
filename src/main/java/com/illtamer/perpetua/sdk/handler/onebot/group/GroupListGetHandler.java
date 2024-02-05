package com.illtamer.perpetua.sdk.handler.onebot.group;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.entity.transfer.receive.Group;
import com.illtamer.perpetua.sdk.handler.onebot.AbstractAPIHandler;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取群列表
 * */
public class GroupListGetHandler extends AbstractAPIHandler<List<Map<String, Object>>> {

    @Getter
    @Nullable
    private static List<Group> cachedGroups;

    public GroupListGetHandler() {
        super("get_group_list");
    }

    @NotNull
    public static List<Group> parse(@NotNull Response<List<Map<String, Object>>> response) {
        final Gson gson = new Gson();
        final JsonArray array = gson.fromJson(gson.toJson(response.getData()), JsonArray.class);
        List<Group> groups = new ArrayList<>(array.size());
        array.forEach(object -> groups.add(gson.fromJson(object, Group.class)));
        return cachedGroups = groups;
    }

}
