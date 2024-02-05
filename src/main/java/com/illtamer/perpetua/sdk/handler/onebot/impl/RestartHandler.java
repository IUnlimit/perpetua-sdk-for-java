package com.illtamer.perpetua.sdk.handler.onebot.impl;

import com.illtamer.perpetua.sdk.handler.onebot.AbstractAPIHandler;
import lombok.Getter;

/**
 * 重启 OneBot 实现
 * @apiNote TODO 此实现将由 Perpetua 提供
 * */
@Getter
@Deprecated
public class RestartHandler extends AbstractAPIHandler<Object> {

    /**
     * 要延迟的毫秒数
     * <p>
     * 如果默认情况下无法重启，可以尝试设置延迟为 2000 左右
     * */
    private Integer delay;

    public RestartHandler() {
        super("set_restart");
    }

    public RestartHandler setDelay(Integer delay) {
        this.delay = delay;
        return this;
    }

}
