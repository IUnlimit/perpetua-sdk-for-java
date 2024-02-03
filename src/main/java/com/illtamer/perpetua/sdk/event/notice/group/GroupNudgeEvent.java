package com.illtamer.perpetua.sdk.event.notice.group;//package com.illtamer.perpetua.sdk.event.notice.group;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.event.notice.GroupNoticeEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 群内戳一戳事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.NOTICE,
        secType = "notify",
        subType = "poke"
)
public class GroupNudgeEvent extends GroupNoticeEvent {

    /**
     * 提示类型
     * <p>
     * poke
     * */
    @SerializedName("sub_type")
    private String subType;

    /**
     * 被戳者 QQ 号
     * */
    @SerializedName("target_id")
    private Long targetId;

}
