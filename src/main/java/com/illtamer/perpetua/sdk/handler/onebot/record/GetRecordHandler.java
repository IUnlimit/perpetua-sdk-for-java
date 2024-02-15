package com.illtamer.perpetua.sdk.handler.onebot.record;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;
import lombok.Getter;

import java.util.Map;

/**
 * 获取语音
 * */
@Getter
@Deprecated
public class GetRecordHandler extends AbstractWSAPIHandler<Map<String, Object>> {

    /**
     * 收到的语音文件名（消息段的 file 参数）
     * <p>
     * 如 0B38145AA44505000B38145AA4450500.silk
     * */
    private String file;

    /**
     * 要转换到的格式
     * @apiNote 目前支持 mp3、amr、wma、m4a、spx、ogg、wav、flac
     * */
    @SerializedName("out_format")
    private String outFormat;

    public GetRecordHandler() {
        super("get_record");
    }

    public GetRecordHandler setFile(String file) {
        this.file = file;
        return this;
    }

    public GetRecordHandler setOutFormat(String outFormat) {
        this.outFormat = outFormat;
        return this;
    }

}
