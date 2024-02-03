package com.illtamer.perpetua.sdk.entity.transfer.receive;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class MessageEntity {

    private boolean group;

    @SerializedName("group_id")
    private Long groupId;

    private com.illtamer.perpetua.sdk.message.Message message;

    @SerializedName("message_id")
    private Integer messageId;

    @SerializedName("message_id_v2")
    private String messageIdV2;

    @SerializedName("message_seq")
    private Double messageSeq;

    @SerializedName("message_type")
    private String messageType;

    @SerializedName("real_id")
    private Double realId;

    private MessageSender sender;

}
