package com.illtamer.perpetua.sdk.handler.onebot.group;

import com.google.gson.annotations.SerializedName;
import com.illtamer.perpetua.sdk.handler.AbstractWSAPIHandler;
import lombok.Getter;

@Getter
public class GroupCardSetHandler extends AbstractWSAPIHandler<Object> {

    /**
     * 群号
     * */
    @SerializedName("group_id")
    private Long groupId;

    /**
     * 要设置的 QQ 号
     * */
    @SerializedName("user_id")
    private Long userId;

    /**
     * 群名片内容, 不填或空字符串表示删除群名片
     * */
    private String card;

    public GroupCardSetHandler() {
        super("set_group_card");
    }

    public GroupCardSetHandler setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public GroupCardSetHandler setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public GroupCardSetHandler setCard(String card) {
        this.card = card;
        return this;
    }

}
