package com.illtamer.perpetua.sdk.entity.transfer.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Perpetua 客户端
 * */
@Data
public class Client {

    @SerializedName("app_id")
    private String appId;

    @SerializedName("client_name")
    private String clientName;

}
