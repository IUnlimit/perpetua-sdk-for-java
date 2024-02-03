package com.illtamer.perpetua.sdk.event.notice.group;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.event.notice.GroupNoticeEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 群成员头衔变更事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.NOTICE,
        secType = "notify",
        subType = {"title"}
)
@Deprecated
public class GroupMemberTitleChangeEvent extends GroupNoticeEvent {

    /**
     * 事件子类型
     * <p>
     * title
     * */
    @SerializedName("sub_type")
    private String subType;

    /**
     * 获得的新头衔
     * */
    private String title;

}
