package com.illtamer.perpetua.sdk.entity.transfer.segment;

import com.illtamer.perpetua.sdk.entity.TransferEntity;
import lombok.Data;

@Data
public class Forward implements TransferEntity {

    /**
     * 合并转发ID
     * <p>
     * 需要通过 /get_forward_msg API获取转发的具体内容
     * */
    private String id;

}
