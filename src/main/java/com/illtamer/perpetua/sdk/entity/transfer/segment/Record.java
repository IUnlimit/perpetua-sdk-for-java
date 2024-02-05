package com.illtamer.perpetua.sdk.entity.transfer.segment;

import com.illtamer.perpetua.sdk.entity.TransferEntity;
import lombok.Data;

@Data
public class Record implements TransferEntity {

    /**
     * 语音文件名
     * */
    private String file;

    /**
     * 默认 0, 为 1 表示变声
     * */
    private Integer magic;

    /**
     * 语音 URL
     * */
    private String url;

}
