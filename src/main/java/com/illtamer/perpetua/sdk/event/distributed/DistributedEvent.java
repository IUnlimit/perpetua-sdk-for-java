package com.illtamer.perpetua.sdk.event.distributed;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.annotation.Coordinates;
import com.illtamer.perpetua.sdk.entity.transfer.entity.Client;
import com.illtamer.perpetua.sdk.event.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分布式事件
 * */
@Setter
@Getter
@ToString(callSuper = true)
@Coordinates(
        postType = Coordinates.PostType.DISTRIBUTED,
        secType = "*"
)
public class DistributedEvent extends Event {

    /**
     * 分布式类型
     * */
    @SerializedName("distributed_type")
    private String distributedType;

    /**
     * 发起方客户端信息
     * */
    private Client client;

    /**
     * 此次客户端广播事件的唯一id
     * */
    private String uuid;

}
