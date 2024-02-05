package com.illtamer.perpetua.sdk.entity.transfer.segment;

import com.illtamer.perpetua.sdk.entity.TransferEntity;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

/**
 * JSON 消息
 * <p>
 * 群公告等富文本消息
 * */
@Data
public class JSON implements TransferEntity {

    /**
     * json 字符串，需转义 {@link com.illtamer.perpetua.sdk.util.AdapterUtil#parse(String)}
     * */
    private String data;

    /**
     * 默认为0, 走小程序通道, 不为0则走富文本通道发送
     * */
    @Nullable
    private Short resid;

}
