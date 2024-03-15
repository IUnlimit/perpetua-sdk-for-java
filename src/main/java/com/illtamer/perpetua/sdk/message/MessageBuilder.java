package com.illtamer.perpetua.sdk.message;

import com.illtamer.perpetua.sdk.Pair;
import com.illtamer.perpetua.sdk.annotation.SendOnly;
import com.illtamer.perpetua.sdk.entity.TransferEntity;
import com.illtamer.perpetua.sdk.entity.enumerate.FaceType;
import com.illtamer.perpetua.sdk.entity.enumerate.MusicType;
import com.illtamer.perpetua.sdk.entity.enumerate.PokeType;
import com.illtamer.perpetua.sdk.exception.ExclusiveMessageException;
import com.illtamer.perpetua.sdk.handler.onebot.message.GroupForwardSendHandler;
import com.illtamer.perpetua.sdk.util.Maps;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 消息构建
 * @apiNote <a href="https://github.com/botuniverse/onebot-11/blob/master/message/segment.md">...</a>
 * */
@SuppressWarnings("unchecked")
public class MessageBuilder {

    private final Message message;

    private MessageBuilder(Message message) {
        this.message = message;
    }

    // text

    public MessageBuilder text(String text) {
        return text(text, message.getSize() != 0);
    }

    public MessageBuilder text(String text, boolean newline) {
        message.add("text", Maps.of(
                "text", newline ? '\n' + text : text
        ));
        return this;
    }

    // face

    /**
     * QQ表情
     * @param type QQ 表情类型
     * @apiNote <a href="https://github.com/kyubotics/coolq-http-api/wiki/%E8%A1%A8%E6%83%85-CQ-%E7%A0%81-ID-%E8%A1%A8">...</a>
     * */
    public MessageBuilder face(FaceType type) {
        message.add("face", Maps.of(
                "id", type.getId()
        ));
        return this;
    }

    // image

    /**
     * 普通图片
     * @param name 图片文件名
     * @param url 图片 URL
     * */
    public MessageBuilder image(String name, String url) {
        return image(name, url, null);
    }

    /**
     * 普通图片
     * @param name 图片文件名
     * @param url 图片 URL
     * @param subType 图片子类型, 只出现在群聊.
     * */
    public MessageBuilder image(String name, String url, @Nullable Integer subType) {
        message.add("image", Maps.of(
                "file", name,
                "url", url,
                "subType", nullableToString(subType)
        ));
        return this;
    }

    /**
     * 闪照
     * @param name 图片文件名
     * @param url 图片 URL
     * */
    public MessageBuilder flashImage(String name, String url) {
        return flashImage(name, url, null);
    }

    /**
     * 闪照
     * @param name 图片文件名
     * @param subType 图片子类型, 只出现在群聊.
     * @param url 图片 URL
     * */
    public MessageBuilder flashImage(String name, String url, @Nullable Integer subType) {
        message.add("image", Maps.of(
                "file", name,
                "url", url,
                "subType", nullableToString(subType),
                "type", "flash"
        ));
        return this;
    }

    // record

    /**
     * 语音
     * @param file 语音文件名(URL)
     * */
    public Message record(String file) {
        return record(file, null, null, null, null);
    }

    /**
     * 语音
     * @param file 语音文件名(URL)
     * @param magic 发送时可选, 默认 0, 设置为 1 表示变声
     * @param cache 只在通过网络 URL 发送时有效, 表示是否使用已缓存的文件, 默认 1
     * @param proxy 只在通过网络 URL 发送时有效, 表示是否通过代理下载文件 ( 需通过环境变量或配置文件配置代理 ) , 默认 1
     * @param timeout 只在通过网络 URL 发送时有效, 单位秒, 表示下载网络文件的超时时间 , 默认不超时
     * */
    @Deprecated
    public Message record(String file, @Nullable String magic, @Nullable Integer cache, @Nullable Integer proxy, @Nullable Integer timeout) {
        message.addExclusive("record", Maps.of(
                "file", file,
                "magic", magic,
                "cache", cache,
                "proxy", proxy,
                "timeout", timeout
        ));
        return build();
    }

    // video

    /**
     * 短视频
     * @param file 视频地址, 支持http和file发送
     * @param cover 视频封面, 支持http, file和base64发送, 格式必须为jpg
     * @param c 通过网络下载视频时的线程数, 默认单线程. (在资源不支持并发时会自动处理)
     * @apiNote go-cqhttp-v0.9.38 起开始支持发送，需要依赖ffmpeg
     * */
    public Message video(String file, String cover, @Nullable Integer c) {
        message.addExclusive("video", Maps.of(
                "file", file,
                "cover", cover,
                "c", c
        ));
        return build();
    }

    // at

    /**
     * @ 某人
     * @param qq @的 QQ 号, all 表示全体成员
     * */
    public MessageBuilder at(Long qq) {
        message.add("at", Maps.of(
                "qq", qq.toString(),
                "name", qq.toString()
        ));
        return this;
    }

    /**
     * @ 某人
     * @param qq @的 QQ 号, all 表示全体成员
     * @param name 当在群中找不到此QQ号的名称时才会生效
     * */
    public MessageBuilder at(Long qq, @SendOnly String name) {
        message.add("at", Maps.of(
                "qq", qq.toString(),
                "name", name
        ));
        return this;
    }

    // unsupported
    // 猜拳魔法表情

    // unsupported
    // 掷骰子魔法表情

    // shake

    public MessageBuilder shake() {
        message.add("shake", Collections.EMPTY_MAP);
        return this;
    }

    // poke

    /**
     * 戳一戳
     * @apiNote 发送戳一戳消息无法撤回, 返回的 message id 恒定为 0
     * */
    public Message nudge(PokeType type) {
        message.addExclusive("poke", Maps.of(
                "type", type.getType(),
                "id", type.getId()
        ));
        return build();
    }

    // unsupported
    // 匿名发消息

    // share

    /**
     * 链接分享
     * @param url URL
     * @param title 标题
     * @param content 发送时可选, 内容描述
     * @param image 发送时可选, 图片 URL
     * @throws ExclusiveMessageException 单一消息类型异常
     * */
    public Message share(String url, String title, @SendOnly(nullable = true) String content, @SendOnly(nullable = true) String image) {
        message.addExclusive("share", Maps.of(
                "url", url,
                "title", title,
                "content", content,
                "image", image
        ));
        return build();
    }

    // unsupported
    // 推荐好友/群

    // unsupported
    // 位置

    // music

    /**
     * 音乐分享
     * @param type 歌曲来源
     * @param id 歌曲 ID
     * @throws ExclusiveMessageException 单一消息类型异常
     * */
    @SendOnly
    public Message music(MusicType type, String id) {
        message.addExclusive("music", Maps.of(
                "type", type.getValue(),
                "id", id
        ));
        return build();
    }

    /**
     * 音乐自定义分享
     * @param url 点击后跳转目标 URL
     * @param audio 音乐 URL
     * @param title 标题
     * @param content 发送时可选, 内容描述
     * @param image 发送时可选, 图片 URL
     * @throws ExclusiveMessageException 单一消息类型异常
     * */
    @SendOnly
    public Message customMusic(String url, String audio, String title, String content, String image) {
        message.addExclusive("music", Maps.of(
                "type", "custom",
                "url", url,
                "audio", audio,
                "title", title,
                "content", content,
                "image", image
        ));
        return build();
    }

    // reply

    /**
     * 回复
     * @param id 回复时所引用的消息id
     * */
    public MessageBuilder reply(Integer id) {
        return reply(id, null, null, null, null);
    }

    /**
     * 自定义回复
     * @param text 自定义回复的信息
     * @param qq 自定义回复时的自定义QQ, 如果使用自定义信息必须指定.
     * @param time 自定义回复时的时间, 格式为Unix时间
     * @param seq 起始消息序号, 可通过 get_msg 获得
     * @apiNote 如果 id 和 text 同时存在, 将采用自定义reply并替换原有信息 如果 id 获取失败, 将回退到自定义reply
     * @deprecated 经测试，在不指定 id 的前提下无法正确发送自定义回复，请转用 {@link #reply(Integer, String, Long, Long, Long)}}
     * */
    @Deprecated
    public MessageBuilder customReply(String text, Long qq, Long time, Long seq) {
        return reply(null, text, qq, time, seq);
    }

    /**
     * 回复(可自定义)
     * @param id 回复时所引用的消息id
     * @param text 自定义回复的信息
     * @param qq 自定义回复时的自定义QQ, 如果使用自定义信息必须指定.
     * @param time 自定义回复时的时间, 格式为Unix时间
     * @param seq 起始消息序号, 可通过 get_msg 获得
     * @apiNote 如果 id 和 text 同时存在, 将采用自定义reply并替换原有信息 如果 id 获取失败, 将回退到自定义reply
     * */
    @Deprecated
    public MessageBuilder reply(@Nullable Integer id, @Nullable String text, @Nullable Long qq, @Nullable Long time, @Nullable Long seq) {
        message.add("reply", Maps.of(
                "id", nullableToString(id),
                "text", text,
                "qq", nullableToString(qq),
                "time", nullableToString(time),
                "seq", nullableToString(seq)
        ));
        return this;
    }

    // node

    /**
     * 添加合并转发消息节点
     * @param id 转发消息id
     * <p>
     * 直接引用他人的消息合并转发, 实际查看顺序为原消息发送顺序
     * @apiNote 此消息仅能使用 {@link GroupForwardSendHandler} 发送
     * */
    @SendOnly
    public MessageBuilder messageNode(Integer id) {
        message.add("node", Maps.of(
                "id", nullableToString(id)
        ));
        return this;
    }

    /**
     * 添加自定义合并转发消息节点
     * @param name 发送者显示名字
     * <p>
     * 用于自定义消息 (自定义消息并合并转发, 实际查看顺序为自定义消息段顺序)
     * @param uin 发送者QQ号
     * <p>
     * 用于自定义消息
     * @param content 具体消息
     * <p>
     * 用于自定义消息 不支持转发套娃
     * @param seq 具体消息
     * <p>
     * 用于自定义消息
     * @apiNote 此消息仅能使用 {@link GroupForwardSendHandler} 发送
     * */
    @SendOnly
    public MessageBuilder customMessageNode(String name, Long uin, Message content, @Nullable Message seq) {
        message.add("node", Maps.of(
                "name", name,
                "uin", nullableToString(uin),
                "content", content,
                "seq", seq
        ));
        return this;
    }

    // xml

    /**
     * 添加xml消息
     * @param data xml消息内容
     * */
    public MessageBuilder xml(String data) {
        message.add("xml", Maps.of(
                "data", data
        ));
        return this;
    }

    // json

    /**
     * 添加json消息
     * @param data json消息内容
     * */
    public MessageBuilder json(String data) {
        message.add("json", Maps.of(
                "data", data
        ));
        return this;
    }

    // card image

    /**
     * 一种xml的图片消息（装逼大图）
     * @param file 和image的file字段对齐, 支持也是一样的
     * @apiNote xml 接口的消息都存在风控风险, 请自行兼容发送失败后的处理 ( 可以失败后走普通图片模式 )
     * */
    @SendOnly
    @Deprecated
    public Message xmlImage(String file) {
        return xmlImage(file, null, null, null, null, null, null);
    }

    /**
     * 一种xml的图片消息（装逼大图）
     * @param file 和image的file字段对齐, 支持也是一样的
     * @param minwidth 默认不填为400, 最小width
     * @param minheight 默认不填为400, 最小height
     * @param maxwidth 默认不填为500, 最大width
     * @param maxheight 默认不填为1000, 最大height
     * @param source 分享来源的名称, 可以留空
     * @param icon 分享来源的icon图标url, 可以留空
     * @apiNote xml 接口的消息都存在风控风险, 请自行兼容发送失败后的处理 ( 可以失败后走普通图片模式 )
     * */
    @SendOnly
    @Deprecated
    public Message xmlImage(String file, @Nullable Long minwidth, @Nullable Long minheight, @Nullable Long maxwidth, @Nullable Long maxheight, @Nullable String source, @Nullable String icon) {
        message.addExclusive("cardimage", Maps.of(
                "file", file,
                "minwidth", nullableToString(minwidth),
                "minheight", nullableToString(minheight),
                "maxwidth", nullableToString(maxwidth),
                "maxheight", nullableToString(maxheight),
                "source", source,
                "icon", icon
        ));
        return build();
    }

    /**
     * 文本转语音
     * @apiNote 通过TX的TTS接口, 采用的音源与登录账号的性别有关
     * */
    @SendOnly
    @Deprecated
    public Message speak(String text) {
        message.addExclusive("tts", Maps.of(
                "text", text
        ));
        return build();
    }

    /**
     * 添加消息类型实例
     * */
    public MessageBuilder add(TransferEntity entity) {
        Pair<String, Map<String, @Nullable Object>> pair = MessageChain.entityToProperty(entity);
        property(pair.getKey(), pair.getValue());
        return this;
    }

    /**
     * 添加其他消息的所有内容
     * @apiNote 注意：不是合并消息！相当于浅 clone
     * */
    public MessageBuilder addAll(Message message) {
        message.entryList().forEach(pair -> property(pair.getKey(), pair.getValue()));
        return this;
    }

    /**
     * @apiNote 判断 Text 类型时需注意：如生成 Text 时 message 不为空，则会自动在行首加上换行符 \n。您也可以使用
     * {@link #text(String, boolean)} 来避免不必要的符号添加。
     * */
    public MessageBuilder removeIf(Predicate<TransferEntity> predicate) {
        message.removeIf(predicate);
        return this;
    }

    MessageBuilder property(String type, Map<String, @Nullable Object> args) {
        message.add(type, args);
        return this;
    }

    public boolean empty() {
        return message.getSize() == 0;
    }

    public Message build() {
        return message;
    }

    /**
     * 构建 cq 码消息
     * @apiNote 请根据 onebot 实现具体选择构建的消息类型
     * */
    public static MessageBuilder cq() {
        return new MessageBuilder(new CQMessage());
    }

    /**
     * 构建 json 消息
     * @apiNote 请根据 onebot 实现具体选择构建的消息类型
     * */
    public static MessageBuilder json() {
        return new MessageBuilder(new JsonMessage());
    }

    public static MessageBuilder parse(Message message) {
        return new MessageBuilder(message);
    }

    @Nullable
    private static String nullableToString(@Nullable Object obj) {
        return obj != null ? obj.toString() : null;
    }

}
