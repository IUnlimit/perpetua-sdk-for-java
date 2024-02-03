package com.illtamer.perpetua.sdk.event.meta;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.event.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 元事件上报事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.META_EVENT,
        secType = "*"
)
public class MetaEvent extends Event {

    /**
     * 元数据类型
     * */
    @SerializedName("meta_event_type")
    private String metaEventType;

}
