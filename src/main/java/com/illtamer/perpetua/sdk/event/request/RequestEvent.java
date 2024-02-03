package com.illtamer.perpetua.sdk.event.request;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.event.Event;
import com.illtamer.perpetua.sdk.exception.UnsupportException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

/**
 * 请求上报 / 通知上报 事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.REQUEST,
        secType = "*"
)
public class RequestEvent extends Event {

    /**
     * 请求类型
     * */
    @SerializedName("request_type")
    private String requestType;

    /**
     * 同意请求
     * @param remark 添加后的好友备注
     * */
    public void approve(@Nullable String remark) {
        throw new UnsupportException();
    }

    /**
     * 拒绝请求
     * */
    public void reject() {
        throw new UnsupportException();
    }

}
