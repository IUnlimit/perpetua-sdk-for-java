package com.illtamer.perpetua.sdk.event.notice.friend;

import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.event.notice.FriendNoticeEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 好友添加提醒事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.NOTICE,
        secType = "friend_add"
)
public class FriendAddNoticeEvent extends FriendNoticeEvent {

}
