package com.illtamer.perpetua.sdk.message;

import com.google.gson.internal.LinkedTreeMap;
import com.illtamer.perpetua.sdk.Pair;
import com.illtamer.perpetua.sdk.entity.TransferEntity;
import com.illtamer.perpetua.sdk.exception.ExclusiveMessageException;
import com.illtamer.perpetua.sdk.util.Assert;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * CQ Code 消息
 * <p>
 * 用于被动接收消息
 * */
@Log
public class CQMessage extends Message {

    private final List<Pair<String, Map<String, @NotNull Object>>> list;
    private final MessageChain messageChain;

    private boolean textOnly;

    protected CQMessage() {
        this(new ArrayList<>(), true);
    }

    private CQMessage(List<Pair<String, Map<String, @NotNull Object>>> list, boolean textOnly) {
        this.list = list;
        this.textOnly = textOnly;
        this.messageChain = new MessageChain();
    }

    @Override
    @NotNull
    public MessageChain getMessageChain() {
        return messageChain;
    }

    @Override
    @NotNull
    public List<String> getCleanMessage() {
        return list.stream()
                .filter(entry -> "text".equals(entry.getKey()))
                .map(entry -> (String) entry.getValue().get("text"))
                .collect(Collectors.toList());
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public boolean isTextOnly() {
        return textOnly;
    }

    @Override
    public Message clone() {
        List<Pair<String, Map<String, Object>>> copyList = new LinkedList<>();
        for (Pair<String, Map<String, Object>> pair : list) {
            Map<String, Object> copyMap = new LinkedTreeMap<>();
            copyMap.putAll(pair.getValue());
            copyList.add(new Pair<>(pair.getKey(), copyMap));
        }
        return new CQMessage(copyList, textOnly);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Pair<String, Map<String, @NotNull Object>> entry : list) {
            if ("text".equals(entry.getKey()))
                builder.append((String) entry.getValue().get("text"));
            else {
                builder.append("[CQ:").append(entry.getKey());
                for (Map.Entry<String, Object> dataEntry : entry.getValue().entrySet())
                    builder.append(',').append(dataEntry.getKey()).append('=').append((String) dataEntry.getValue());
                builder.append(']');
            }
        }
        return builder.toString();
    }

    @Override
    protected void add(String type, Map<String, @Nullable Object> data) {
        if (textOnly)
            textOnly = "text".equals(type);
        Pair<String, Map<String, @NotNull Object>> entryObject = new Pair<>(type, new LinkedTreeMap<>());
        final List<Map.Entry<String, Object>> notnull = data.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toList());
        // notnull map can be empty
        messageChain.boltOn(type, notnull);

        notnull.forEach(entry -> {
                    Object value = entry.getValue();
                    Object element;
                    if (value instanceof Message)
                        element = ((CQMessage) value).list;
                    else
                        element = value.toString();
                    entryObject.getValue().put(entry.getKey(), element);
                });
        list.add(entryObject);
    }

    @Override
    protected void removeIf(Predicate<TransferEntity> predicate) {
        messageChain.removeWith(predicate, index -> list.remove(index.intValue()));
    }

    @Override
    protected void addExclusive(String type, Map<String, @Nullable Object> data) {
        if (!list.isEmpty()) {
            for (Pair<String, Map<String, @NotNull Object>> entry : list) {
                // 不检查其他消息节点
                if ("node".equals(entry.getKey())) continue;
                throw new ExclusiveMessageException(type);
            }
        }
        add(type, data);
    }

    @Override
    protected List<Pair<String, Map<String, @NotNull Object>>> entryList() {
        return list;
    }

    /**
     * cq 码反序列化
     * */
    public static CQMessage deserialize(String cqString) {
        final char[] chars = cqString.toCharArray();
        final MessageBuilder builder = MessageBuilder.cq();
        for (int i = 0; i < chars.length; ++ i) {
            if (chars[i] == '[') {
                for (int j = i; j < chars.length; ++ j) {
                    if (chars[j] != ']') continue;
                    doDeserialize(cqString.substring(i+1, j), builder, false);
                    i = j;
                    break;
                }
            } else {
                for (int j = i; j < chars.length; ++ j) {
                    boolean end = false;
                    if (chars[j] != '[' && (!(end = j + 1 == chars.length))) continue;
                    doDeserialize(end ? cqString.substring(i, j+1) : cqString.substring(i, j), builder, true);
                    i = end ? j : j-1;
                    break;
                }
            }
        }
        return (CQMessage) builder.build();
    }

    private static void doDeserialize(String cqCode, MessageBuilder builder, boolean property) {
        if (property)
            builder.text(cqCode, false);
        else {
            final String[] params = cqCode.split(",");
            Assert.isTrue(params[0].startsWith("CQ:"), "CQ Code format error");
            Map<String, Object> args = new LinkedTreeMap<>();
            for (int i = 1; i < params.length; ++ i) {
                try {
                    final String[] split = params[i].split("=");
                    if (split.length < 2) continue;
                    args.put(split[0], split[1]);
                } catch (Exception e) {
                    log.severe("Error occurred in deserializing CQ(" + cqCode +")");
                    e.printStackTrace();
                }
            }
            builder.property(params[0].substring(3), args);
        }
    }

}
