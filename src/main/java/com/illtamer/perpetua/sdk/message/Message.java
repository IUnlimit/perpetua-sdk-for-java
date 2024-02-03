package com.illtamer.perpetua.sdk.message;

import com.illtamer.perpetua.sdk.Pair;
import com.illtamer.perpetua.sdk.entity.TransferEntity;
import com.illtamer.perpetua.sdk.exception.ExclusiveMessageException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public abstract class Message implements Cloneable {

   /**
    * 获取消息元素链
    * */
   @NotNull
   abstract public MessageChain getMessageChain();

   /**
    * 仅获取消息中的 text 元素
    * */
   @NotNull
   abstract public List<String> getCleanMessage();

   abstract public int getSize();

   /**
    * 是否仅为文本消息
    * */
   abstract public boolean isTextOnly();

   abstract public Message clone();

   abstract public String toString();

   /**
    * 可重复种类消息添加
    * */
   abstract protected void add(String type, Map<String, @Nullable Object> data);

   abstract protected void removeIf(Predicate<TransferEntity> predicate);

   /**
    * @throws ExclusiveMessageException 单一消息类型异常
    * */
   abstract protected void addExclusive(String type, Map<String, @Nullable Object> data);

   abstract protected List<Pair<String, Map<String, @NotNull Object>>> entryList();

}
