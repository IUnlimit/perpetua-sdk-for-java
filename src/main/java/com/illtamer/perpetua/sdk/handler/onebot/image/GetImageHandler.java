package com.illtamer.perpetua.sdk.handler.onebot.image;

import com.google.gson.Gson;
import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.entity.transfer.receive.ImageEntity;
import com.illtamer.perpetua.sdk.handler.onebot.AbstractAPIHandler;
import lombok.Getter;

import java.util.Map;

/**
 * 获取图片信息
 * */
@Getter
@Deprecated
public class GetImageHandler extends AbstractAPIHandler<Map<String, Object>> {

    private String file;

    public GetImageHandler() {
        super("get_image");
    }

    public GetImageHandler setFile(String file) {
        this.file = file;
        return this;
    }

    public static ImageEntity parse(Response<Map<String, Object>> response) {
        final Gson gson = new Gson();
        return gson.fromJson(gson.toJson(response.getData()), ImageEntity.class);
    }

}
