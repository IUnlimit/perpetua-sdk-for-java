package com.illtamer.perpetua.sdk.handler;

import com.illtamer.perpetua.sdk.Response;
import com.illtamer.perpetua.sdk.entity.TransferEntity;
import com.illtamer.perpetua.sdk.entity.transfer.entity.*;
import com.illtamer.perpetua.sdk.entity.transfer.segment.Text;
import com.illtamer.perpetua.sdk.exception.APIInvokeException;
import com.illtamer.perpetua.sdk.handler.onebot.account.GetLoginInfoHandler;
import com.illtamer.perpetua.sdk.handler.onebot.friend.*;
import com.illtamer.perpetua.sdk.handler.onebot.group.*;
import com.illtamer.perpetua.sdk.handler.onebot.image.GetImageHandler;
import com.illtamer.perpetua.sdk.handler.onebot.impl.GetStatusHandler;
import com.illtamer.perpetua.sdk.handler.onebot.impl.GetVersionHandler;
import com.illtamer.perpetua.sdk.handler.onebot.message.*;
import com.illtamer.perpetua.sdk.message.Message;
import com.illtamer.perpetua.sdk.message.MessageBuilder;
import com.illtamer.perpetua.sdk.websocket.OneBotConnection;

import java.util.List;
import java.util.Map;

/**
 * 开放 OneBot API 调用
 * @apiNote 所有方法均有可能抛出 {@link APIInvokeException}
 * */
public class OpenAPIHandling {

    // account

    /**
     * 获取登录账号信息
     * */
    public static LoginInfo getLoginInfo() {
        return GetLoginInfoHandler.parse(
                new GetLoginInfoHandler().request().getData());
    }

    // friend

    /**
     * 删除好友
     * @param friendId 好友 QQ 号
     * */
    @Deprecated
    public static void deleteFriend(long friendId) {
        new DeleteFriendHandler()
                .setFriendId(friendId)
                .request();
    }

    /**
     * 处理好友申请
     * @param flag 加好友请求的 flag（需从上报的数据中获得）
     * @param approve 是否同意请求
     * */
    public static void handleFriendRequest(String flag, boolean approve) {
        handleFriendRequest(flag, approve, null);
    }

    /**
     * 处理好友申请
     * @param flag 加好友请求的 flag（需从上报的数据中获得）
     * @param approve 是否同意请求
     * @param remark 添加后的好友备注（仅在同意时有效）
     * */
    public static void handleFriendRequest(String flag, boolean approve, String remark) {
        new FriendAddRequestHandler()
                .setFlag(flag)
                .setApprove(approve)
                .setRemark(remark)
                .request();
    }

    /**
     * 获取好友列表
     * */
    public static List<Friend> getFriendList() {
        return GetFriendListHandler.parse(
                new GetFriendListHandler().request());
    }

    /**
     * 获取陌生人信息
     * @param userId 用户 QQ
     * */
    @Deprecated
    public static Stranger getStranger(long userId) {
        return getStranger(userId, true);
    }

    /**
     * 获取陌生人信息
     * @param userId 用户 QQ
     * @param cache 是否使用缓存
     * */
    @Deprecated
    public static Stranger getStranger(long userId, boolean cache) {
        return GetStrangerHandler.parse(new GetStrangerHandler()
                .setUserId(userId)
                .setNoCache(!cache)
                .request());
    }

    /**
     * 发送好友赞
     * @param userId 对方账号
     * @param times 赞的次数，每个好友每天最多 10 次
     * */
    public static void sendLike(long userId, int times) {
        new SendLikeHandler()
                .setUserId(userId)
                .setTimes(times);
    }

    // group

    /**
     * 处理加群请求／邀请
     * @param flag 加群请求的 flag（需从上报的数据中获得）
     * @param subType add 或 invite，请求类型（需要和上报消息中的 sub_type 字段相符）
     * @param approve 是否同意请求
     * */
    public static void handleGroupRequest(String flag, String subType, boolean approve) {
        handleGroupRequest(flag, subType, approve, null);
    }

    /**
     * 处理加群请求／邀请
     * @param flag 加群请求的 flag（需从上报的数据中获得）
     * @param subType add 或 invite，请求类型（需要和上报消息中的 sub_type 字段相符）
     * @param approve 是否同意请求
     * @param reason 拒绝理由（仅在拒绝时有效）
     * */
    public static void handleGroupRequest(String flag, String subType, boolean approve, String reason) {
        new GroupAddRequestHandler()
                .setFlag(flag)
                .setSubType(subType)
                .setApprove(approve)
                .setReason(reason);
    }

    /**
     * 设置群管理员
     * @param groupId 群号
     * @param userId 操作对象的账号
     * */
    public static void setGroupAdmin(long groupId, long userId) {
        setGroupAdmin(groupId, userId, true);
    }

    /**
     * 设置群管理员
     * @param groupId 群号
     * @param userId 操作对象的账号
     * @param enable true 为设置, false 为取消
     * */
    public static void setGroupAdmin(long groupId, long userId, boolean enable) {
        new GroupAdminSetHandler()
                .setGroupId(groupId)
                .setUserId(userId)
                .setEnable(enable)
                .request();
    }

    /**
     * 群组单人禁言
     * @param groupId 群号
     * @param userId 要禁言的 QQ 号
     * */
    public static void groupBan(long groupId, long userId) {
        groupBan(groupId, userId, 30 * 60);
    }

    /**
     * 群组单人禁言
     * @param groupId 群号
     * @param userId 要禁言的 QQ 号
     * @param duration 禁言时长, 单位秒, 0 表示取消禁言
     * */
    public static void groupBan(long groupId, long userId, int duration) {
        new GroupBanAPIHandler()
                .setGroupId(groupId)
                .setUserId(userId)
                .setDuration(duration)
                .request();
    }

    /**
     * 设置群成员名片
     * @param groupId 群号
     * @param userId 操作对象的账号
     * @param cardName 新群名片
     * */
    public static void setGroupMemberCard(long groupId, long userId, String cardName) {
        new GroupCardSetHandler()
                .setGroupId(groupId)
                .setUserId(userId)
                .setCard(cardName)
                .request();
    }

    /**
     * 群组踢人
     * @param groupId 群号
     * @param userId 被踢的 QQ 号
     * */
    public static void groupKick(long groupId, long userId) {
        groupKick(groupId, userId, false);
    }

    /**
     * 群组踢人
     * @param groupId 群号
     * @param userId 被踢的 QQ 号
     * @param rejectAddRequest 是否拒绝此人的加群请求
     * */
    public static void groupKick(long groupId, long userId, boolean rejectAddRequest) {
        new GroupKickHandler()
                .setGroupId(groupId)
                .setUserId(userId)
                .setRejectAddRequest(rejectAddRequest)
                .request();
    }

    /**
     * 退出群组
     * @param groupId 群号
     * */
    public static void groupLeave(long groupId) {
        groupLeave(groupId, false);
    }

    /**
     * 退出群组
     * @param groupId 群号
     * @param dismiss 是否解散, 如果登录号是群主, 则仅在此项为 true 时能够解散
     * */
    public static void groupLeave(long groupId, boolean dismiss) {
        new GroupLeaveHandler()
                .setGroupId(groupId)
                .setDismiss(dismiss)
                .request();
    }

    /**
     * 获取机器人所在群组
     * */
    public static List<Group> getGroups() {
        return getGroups(false);
    }

    /**
     * 获取机器人所在群组
     * @param cache 是否使用缓存数据
     * @apiNote 使用缓存，数据可能未及时更新
     * */
    public static List<Group> getGroups(boolean cache) {
        final List<Group> cachedGroups = GroupListGetHandler.getCachedGroups();
        if (cache && cachedGroups != null) return cachedGroups;
        return GroupListGetHandler.parse(new GroupListGetHandler().request());
    }

    /**
     * 获取群成员信息
     * @param groupId 群号
     * @param userId 群成员账号
     * */
    public static GroupMember getGroupMember(long groupId, long userId) {
        return getGroupMember(groupId, userId, true);
    }

    /**
     * 获取群成员信息
     * @param groupId 群号
     * @param userId 群成员账号
     * @param cache 是否使用缓存
     * */
    public static GroupMember getGroupMember(long groupId, long userId, boolean cache) {
        return GroupMemberGetHandler.parse(new GroupMemberGetHandler()
                .setGroupId(groupId)
                .setUserId(userId)
                .setNoCache(!cache)
                .request());
    }

    /**
     * 设置群名
     * @param groupId 群号
     * @param name 新群名
     * */
    public static void setGroupName(long groupId, String name) {
        new GroupNameSetHandler()
                .setGroupId(groupId)
                .setGroupName(name)
                .request();
    }

    /**
     * 群组全体禁言
     * @param groupId 群号
     * @param enable 是否禁言
     * */
    public static void groupWholeBan(long groupId, boolean enable) {
        new GroupWholeBanHandler()
                .setGroupId(groupId)
                .setEnable(enable)
                .request();
    }

    // image

    /**
     * @param file 图片缓存文件名
     * */
    @Deprecated
    public static ImageEntity getImage(String file) {
        return GetImageHandler.parse(new GetImageHandler()
                .setFile(file)
                .request());
    }

    // impl

    /**
     * 获取机器人状态信息
     * */
    @Deprecated
    public static Status getStatus() {
        return GetStatusHandler.parse(
                new GetStatusHandler().request());
    }

    /**
     * 获取 OneBot 实现的版本信息
     * */
    public static VersionInfo getVersionInfo() {
        return GetVersionHandler.parse(
                new GetVersionHandler().request());
    }

    // message

    /**
     * 撤回消息
     * @param messageId 消息 ID
     * @throws APIInvokeException 当机器人权限不足（管理员撤回群主消息）时，会抛出 RECALL_API_ERROR
     * */
    public static void deleteMessage(long messageId) {
        new DeleteMsgHandler()
                .setMessageId(messageId)
                .request();
    }

    /**
     * 获取消息
     * @param messageId 消息 ID
     * */
    public static MessageEntity getMessage(long messageId) {
        return GetMsgHandler.parse(new GetMsgHandler()
                .setMessageId(messageId)
                .request());
    }

    /**
     * 发送私人消息
     * @return 消息 ID
     * */
    public static Long sendMessage(String message, long userId) {
        return sendMessage(MessageBuilder.json().text(message).build(), userId);
    }

    /**
     * 发送私人消息
     * @return 消息 ID
     * */
    public static Long sendMessage(Message message, long userId) {
        return sendMessage(message, userId, 0);
    }

    /**
     * 发送私人消息
     * @param limit 最大字符数限制
     * @return 消息 ID
     * */
    @Deprecated
    public static Long sendMessage(Message message, long userId, int limit) {
        Message limited;
        if ((limited = buildLimitMessage(message, limit)) != null) return sendPrivateForwardMessage(limited, userId);
        Response<Map<String, Object>> response = new PrivateMsgSendHandler()
                .setUserId(userId)
                .setMessage(message)
                .request();
        return (Long) response.getData().get("message_id");
    }

    /**
     * 发送临时会话消息
     * @param groupId 消息来源群组
     * @return 消息 ID
     * */
    public static Long sendTempMessage(String message, long userId, long groupId) {
        return sendTempMessage(MessageBuilder.json().text(message).build(), userId, groupId);
    }

    /**
     * 发送临时会话消息
     * @param groupId 消息来源群组
     * @return 消息 ID
     * */
    public static Long sendTempMessage(Message message, long userId, long groupId) {
        return sendTempMessage(message, userId, groupId, 0);
    }

    /**
     * 发送临时会话消息
     * @param groupId 消息来源群组
     * @param limit 最大字符数限制
     * @return 消息 ID
     * */
    @Deprecated
    public static Long sendTempMessage(Message message, long userId, long groupId, int limit) {
        Message limited;
        if ((limited = buildLimitMessage(message, limit)) != null) return sendPrivateForwardMessage(limited, userId);
        Response<Map<String, Object>> response = new PrivateMsgSendHandler()
                .setUserId(userId)
                .setGroupId(groupId)
                .setMessage(message)
                .request();
        return (Long) response.getData().get("message_id");
    }

    /**
     * 发送群消息
     * @return 消息 ID
     * */
    public static Long sendGroupMessage(String message, long groupId) {
        return sendGroupMessage(MessageBuilder.json().text(message).build(), groupId);
    }

    /**
     * 发送群消息
     * @return 消息 ID
     * */
    public static Long sendGroupMessage(Message message, long groupId) {
        return sendGroupMessage(message, groupId, 0);
    }

    /**
     * 发送群消息
     * @return 消息 ID
     * */
    @Deprecated
    public static Long sendGroupMessage(Message message, long groupId, int limit) {
        Message limited;
        if ((limited = buildLimitMessage(message, limit)) != null) return sendGroupForwardMessage(limited, groupId);
        Response<Map<String, Object>> response = new GroupMsgSendHandler()
                .setGroupId(groupId)
                .setMessage(message)
                .request();
        return (Long) response.getData().get("message_id");
    }

    /**
     * 发送自定义合并消息到群
     * @param messageNode 构造的节点消息
     * */
    @Deprecated
    public static Long sendPrivateForwardMessage(Message messageNode, long userId) {
        Response<Map<String, Object>> response = new PrivateForwardSendHandler()
                .setUserId(userId)
                .setMessages(messageNode)
                .request();
        return (Long) response.getData().get("message_id");
    }

    /**
     * 发送自定义合并消息到群
     * @param messageNode 构造的节点消息
     * */
    @Deprecated
    public static Long sendGroupForwardMessage(Message messageNode, long groupId) {
        Response<Map<String, Object>> response = new GroupForwardSendHandler()
                .setGroupId(groupId)
                .setMessages(messageNode)
                .request();
        return (Long) response.getData().get("message_id");
    }

    // record

    /**
     * 构建限长消息
     * @param message 原消息
     * @param limit 限制长度
     * @apiNote 过长消息会被合并到自定义转发消息中
     * */
    @Deprecated
    public static Message buildLimitMessage(Message message, int limit) {
        List<TransferEntity> entities = message.getMessageChain().getEntities();
        int length = entities.stream().filter(e -> e instanceof Text).mapToInt(e -> ((Text) e).getText().length()).sum();
        if (length < limit) return null;

        LoginInfo info = OneBotConnection.getLoginInfo();
        MessageBuilder mergeNodes = MessageBuilder.json();
        MessageBuilder nodeBuilder = MessageBuilder.json();
        int nodeLen = 0;
        for (TransferEntity entity : entities) {
            // TODO 改进内容长度算法，TransferEntity 加 #length 接口
            int entityLen = entity instanceof Text ? ((Text) entity).getText().length() : 20;
            nodeLen += entityLen;
            if (nodeLen <= limit) {
                nodeBuilder.add(entity);
                continue;
            }
            // 非 text 类型不可拆分，直接并入消息
            if (!(entity instanceof Text)) {
                mergeNodes.customMessageNode(info.getNickname(), info.getUserId(), nodeBuilder.build(), null);
                mergeNodes.customMessageNode(info.getNickname(), info.getUserId(), MessageBuilder.json().add(entity).build(), null);
            } else {
                String text = ((Text) entity).getText();
                int split = limit - (nodeLen - entityLen);
                nodeBuilder.text(text.substring(0, split));
                mergeNodes.customMessageNode(info.getNickname(), info.getUserId(), nodeBuilder.build(), null);
                recursionSplitText(text.substring(split), mergeNodes, info, limit);
            }
            nodeLen = 0;
            nodeBuilder = MessageBuilder.json();
        }
        if (!nodeBuilder.empty()) {
            mergeNodes.customMessageNode(info.getNickname(), info.getUserId(), nodeBuilder.build(), null);
        }
        return mergeNodes.build();
    }

    private static void recursionSplitText(String text, MessageBuilder parent, LoginInfo info, int limit) {
        if (text.length() == 0) return;
        if (text.length() <= limit) {
            parent.customMessageNode(info.getNickname(), info.getUserId(), MessageBuilder.json().text(text).build(), null);
            return;
        }
        parent.customMessageNode(info.getNickname(), info.getUserId(), MessageBuilder.json().text(text.substring(0, limit)).build(), null);
        recursionSplitText(text.substring(limit), parent, info, limit);
    }

}
