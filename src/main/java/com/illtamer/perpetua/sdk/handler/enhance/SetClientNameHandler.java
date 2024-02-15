package com.illtamer.perpetua.sdk.handler.enhance;

import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;
import lombok.Getter;

/**
 * 设置当前客户端名称
 * */
@Getter
public class SetClientNameHandler extends AbstractWSAPIHandler<Object> {

    private String name;

    public SetClientNameHandler() {
        super("set_client_name");
    }

    public SetClientNameHandler setName(String name) {
        this.name = name;
        return this;
    }

}
