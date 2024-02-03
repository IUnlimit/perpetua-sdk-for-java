package com.illtamer.perpetua.sdk.entity.transfer.receive;

import lombok.Data;

/**
 * 离线文件信息
 * */
@Data
public class OfflineFile {

    /**
     * 文件名
     * */
    private String name;

    /**
     * 文件大小
     * */
    private Long size;

    /**
     * 下载链接
     * */
    private String url;

}
