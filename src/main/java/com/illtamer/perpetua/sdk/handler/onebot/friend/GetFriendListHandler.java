package com.illtamer.perpetua.sdk.handler.onebot.friend;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.entity.transfer.entity.Friend;
import com.illtamer.perpetua.sdk.handler.onebot.AbstractAPIHandler;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取好友列表
 * */
public class GetFriendListHandler extends AbstractAPIHandler<List<Map<String, Object>>> {

    @Getter
    @Nullable
    private static List<Friend> cachedFriends;

    public GetFriendListHandler() {
        super("get_friend_list");
    }

    @NotNull
    public static List<Friend> parse(@NotNull Response<List<Map<String, Object>>> response) {
        final Gson gson = new Gson();
        final JsonArray array = gson.fromJson(gson.toJson(response.getData()), JsonArray.class);
        List<Friend> friends = new ArrayList<>(array.size());
        array.forEach(object -> friends.add(gson.fromJson(object, Friend.class)));
        return cachedFriends = friends;
    }

}
