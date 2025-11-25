package com.illtamer.perpetua.sdk.handler.onebot.group;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.entity.transfer.entity.GroupMember;
import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

/**
 * 获取群成员列表
 */
@Getter
public class GroupMemberListGetHandler extends AbstractWSAPIHandler<List<Map<String, Object>>> {

    @SerializedName("group_id")
    private Long groupId;

    /**
     * 是否不使用缓存
     * <p>
     * （使用缓存可能更新不及时, 但响应更快）
     * */
    @SerializedName("no_cache")
    private Boolean noCache;

    public GroupMemberListGetHandler() {
        super("get_group_member_list");
    }

    public GroupMemberListGetHandler setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public GroupMemberListGetHandler setNoCache(Boolean noCache) {
        this.noCache = noCache;
        return this;
    }

    @NotNull
    public static List<GroupMember> parse(@NotNull Response<List<Map<String, Object>>> response) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(response.getData()), new TypeToken<List<GroupMember>>() {});
    }

}