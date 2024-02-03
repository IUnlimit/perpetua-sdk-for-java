package com.illtamer.perpetua.sdk.event.notice.group;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.event.notice.GroupNoticeEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 群管理员变动事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.NOTICE,
        secType = "group_admin",
        subType = {"set", "unset"}
)
public class GroupAdminChangeEvent extends GroupNoticeEvent {

    /**
     * 事件子类型, 分别表示设置和取消管理员
     * <p>
     * set、unset
     * */
    @SerializedName("sub_type")
    private String subType;

}
