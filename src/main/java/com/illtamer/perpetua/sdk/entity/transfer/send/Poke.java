package com.illtamer.perpetua.sdk.entity.transfer.send;

import com.illtamer.perpetua.sdk.entity.TransferEntity;
import com.illtamer.perpetua.sdk.entity.enumerate.PokeType;
import lombok.Data;

/**
 * 戳一戳
 * */
@Data
public class Poke implements TransferEntity {

    /**
     * 类型
     * */
    private Integer type;

    /**
     * ID
     * */
    private Integer id;

    /**
     * 表情名
     * */
    private String name;

    public PokeType getType() {
        return PokeType.getPokeType(type, id);
    }

}
