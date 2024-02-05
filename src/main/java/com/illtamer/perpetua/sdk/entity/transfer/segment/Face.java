package com.illtamer.perpetua.sdk.entity.transfer.segment;

import com.illtamer.perpetua.sdk.entity.TransferEntity;
import com.illtamer.perpetua.sdk.entity.enumerate.FaceType;
import lombok.Data;

@Data
public class Face implements TransferEntity {

    /**
     * QQ 表情 ID
     * <p>
     * 见 QQ 表情 ID 表
     * */
    private Integer id;

    public FaceType getType() {
        return FaceType.getFaceType(id);
    }

}
