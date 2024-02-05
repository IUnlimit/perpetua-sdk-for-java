package com.illtamer.perpetua.sdk.event.notice.friend;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.event.notice.FriendNoticeEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 好友消息撤回事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.NOTICE,
        secType = "friend_recall"
)
public class FriendMessageRecallEvent extends FriendNoticeEvent {

    /**
     * 被撤回的消息 ID
     * */
    @SerializedName("message_id")
    private Long messageId;

}
