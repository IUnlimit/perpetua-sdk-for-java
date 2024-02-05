package com.illtamer.perpetua.sdk.entity.transfer.segment;

import com.illtamer.perpetua.sdk.entity.TransferEntity;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

/**
 * 位置
 * */
@Data
public class Location implements TransferEntity {

    /**
     * 纬度
     * */
    private Double lat;

    /**
     * 经度
     * */
    private Double lon;

    /**
     * 发送时可选，标题
     * */
    @Nullable
    private String title;

    /**
     * 发送时可选，内容描述
     * */
    @Nullable
    private String content;

}
