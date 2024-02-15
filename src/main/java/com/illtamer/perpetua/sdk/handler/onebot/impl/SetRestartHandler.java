package com.illtamer.perpetua.sdk.handler.onebot.impl;

import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;
import lombok.Getter;

/**
 * 重启 OneBot 实现
 * @apiNote Perpetua 实现
 * */
@Getter
public class SetRestartHandler extends AbstractWSAPIHandler<Object> {

    /**
     * 要延迟的毫秒数
     * <p>
     * 如果默认情况下无法重启，可以尝试设置延迟为 2000 左右
     * */
    private Integer delay;

    public SetRestartHandler() {
        super("set_restart");
    }

    public SetRestartHandler setDelay(Integer delay) {
        this.delay = delay;
        return this;
    }

}
