package com.illtamer.perpetua.sdk.entity.transfer.receive;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

/**
 * 版本信息
 * */
@Data
public class VersionInfo {

    /**
     * 应用标识
     * */
    @SerializedName("app_name")
    private String appName;

    /**
     * 应用版本
     * */
    @SerializedName("app_version")
    private String appVersion;

    /**
     * OneBot 标准版本 固定值
     * */
    @SerializedName("protocol_version")
    private String protocolVersion;

    /**
     * NTQQ 协议
     * @apiNote Lagrange.OneBot 特有
     * */
    @Nullable
    @SerializedName("nt_protocol")
    private String ntProtocol;

}
