package com.illtamer.perpetua.sdk.entity.transfer.entity;

import lombok.Data;

@Data
public class ImageEntity {

    /**
     * 图片源文件大小
     * */
    private Integer size;

    /**
     * 图片文件原名
     * */
    private String filename;

    /**
     * 图片下载地址
     * */
    private String url;

}
