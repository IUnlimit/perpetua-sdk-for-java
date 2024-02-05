package com.illtamer.perpetua.sdk.event.notice.friend;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.entity.transfer.entity.File;
import com.illtamer.perpetua.sdk.event.notice.NoticeEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 接收离线文件事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.NOTICE,
        secType = "offline_file"
)
@Deprecated
public class OfflineFileEvent extends NoticeEvent {

    /**
     * 发送者id
     * */
    @SerializedName("user_id")
    private Long userId;

    /**
     * 文件数据
     * */
    private File file;

}
