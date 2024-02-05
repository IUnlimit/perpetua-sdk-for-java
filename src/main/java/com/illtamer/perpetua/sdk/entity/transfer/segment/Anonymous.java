package com.illtamer.perpetua.sdk.entity.transfer.segment;

import com.illtamer.perpetua.sdk.entity.TransferEntity;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

/**
 * 匿名发消息
 * @apiNote 当收到匿名消息时，需要通过 消息事件的群消息 的 anonymous 字段判断。
 * */
@Data
public class Anonymous implements TransferEntity {

    /**
     * 表示无法匿名时是否继续发送
     * 0 1
     * */
    @Nullable
    private Integer ignore;

}
