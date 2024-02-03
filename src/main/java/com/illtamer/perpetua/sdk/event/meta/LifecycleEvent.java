package com.illtamer.perpetua.sdk.event.meta;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.annotation.Coordinates;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 生命周期事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.META_EVENT,
        secType = "lifecycle",
        subType = {"enable", "disable", "connect"}
)
public class LifecycleEvent extends MetaEvent {

    /**
     * 事件子类型，分别表示 OneBot 启用、停用、WebSocket 连接成功
     * <p>
     * @apiNote 注意，目前生命周期元事件中，只有 HTTP POST 的情况下
     * 可以收到 enable 和 disable，只有正向 WebSocket 和
     * 反向 WebSocket 可以收到 connect。
     * */
    @SerializedName("sub_type")
    private String subType;

}
