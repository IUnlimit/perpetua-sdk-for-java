package com.illtamer.perpetua.sdk.event.notice;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.entity.transfer.entity.Client;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 客户端在线状态变更
 * @apiNote Perpetua 实现
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.NOTICE,
        secType = "client_status"
)
public class ClientStatusChangeEvent extends NoticeEvent {

    /**
     * 客户端信息
     * */
    private Client client;

    /**
     * 当前是否在线
     * */
    private Boolean online;

    /**
     * 触发事件的客户端是否是自身
     * */
    @SerializedName("self_client")
    private Boolean selfClient;

}
