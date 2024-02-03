package com.illtamer.perpetua.sdk.entity.transfer.send;

import com.illtamer.perpetua.sdk.entity.TransferEntity;
import lombok.Data;

/**
 * 红包
 * */
@Data
public class Redbag implements TransferEntity {

    /**
     * 祝福语/口令
     * <p>
     * 恭喜发财
     * */
    private String title;

}
