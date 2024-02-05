package com.illtamer.perpetua.sdk.entity.transfer.receive;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 好友信息实体类
 * */
@Data
public class Friend {

    /**
     * QQ 号
     * */
    @SerializedName("user_id")
    private Long userId;

    /**
     * 昵称
     * */
    private String nickname;

    /**
     * 备注名
     * */
    private String remark;

}
