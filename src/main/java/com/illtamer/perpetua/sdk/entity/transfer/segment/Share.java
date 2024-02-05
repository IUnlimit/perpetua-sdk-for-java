package com.illtamer.perpetua.sdk.entity.transfer.segment;

import com.illtamer.perpetua.sdk.entity.TransferEntity;
import lombok.Data;

/**
 * 链接分享
 * */
@Data
public class Share implements TransferEntity {

    /**
     * URL
     * */
    private String url;

    /**
     * 标题
     * */
    private String title;

    /**
     * 发送时可选, 内容描述
     * */
    private String content;

    /**
     * 发送时可选, 图片 URL
     * */
    private String image;

}
