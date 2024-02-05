package com.illtamer.perpetua.sdk.handler.onebot.friend;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.entity.transfer.receive.Stranger;
import com.illtamer.perpetua.sdk.handler.onebot.AbstractAPIHandler;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * 获取陌生人信息
 * */
@Getter
@Deprecated
public class GetStrangerHandler extends AbstractAPIHandler<Map<String, Object>> {

    /**
     * QQ 号
     * */
    @SerializedName("user_id")
    private Long userId;

    /**
     * 是否不使用缓存（使用缓存可能更新不及时, 但响应更快）
     * */
    @SerializedName("no_cache")
    private Boolean noCache;

    public GetStrangerHandler() {
        super("get_stranger_info");
    }

    public GetStrangerHandler setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public GetStrangerHandler setNoCache(Boolean noCache) {
        this.noCache = noCache;
        return this;
    }

    @NotNull
    public static Stranger parse(@NotNull Response<Map<String, Object>> response) {
        final Gson gson = new Gson();
        return gson.fromJson(gson.toJson(response.getData()), Stranger.class);
    }

}
