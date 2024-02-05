package com.illtamer.perpetua.sdk.message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.illtamer.perpetua.sdk.Pair;
import com.illtamer.perpetua.sdk.entity.TransferEntity;
import com.illtamer.perpetua.sdk.entity.transfer.segment.*;
import com.illtamer.perpetua.sdk.event.EventResolver;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Message 中所有 {@link TransferEntity} 构成的链表
 * */
public class MessageChain implements Iterable<TransferEntity> {

    private static final Map<String, Class<? extends TransferEntity>> MAPPER;

    @Getter
    private final List<TransferEntity> entities;

    protected MessageChain() {
        this.entities = new ArrayList<>();
    }

    protected void boltOn(String type, List<Map.Entry<String, Object>> data) {
        Class<? extends TransferEntity> clazz = MAPPER.get(type);
        if (clazz == null)
            clazz = Unknown.class;
        try {
            Gson gson = EventResolver.GSON;
            final Map<String, Object> objectMap = data.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            entities.add(gson.fromJson(gson.toJson(objectMap), clazz));
        } catch (Exception e) {
            System.err.println("Unknown error when transferring type: " + type);
            e.printStackTrace();
        }
    }

    protected void removeWith(Predicate<TransferEntity> predicate, Consumer<Integer> consumer) {
        final Iterator<TransferEntity> each = entities.iterator();
        int index = 0;
        while (each.hasNext()) {
            final TransferEntity entity = each.next();
            if (predicate.test(entity)) {
                each.remove();
                consumer.accept(index);
            }
            ++ index;
        }
    }

    // 消息类型实例序列化
    protected static Pair<String, Map<String, @Nullable Object>> entityToProperty(TransferEntity entity) {
        String key = entity.getClass().getSimpleName().toLowerCase();
        Gson gson = EventResolver.GSON;
        // fixed by EventResolver#injectGson, no need to specify type token
        Map<String, @Nullable Object> value = gson.fromJson(gson.toJson(entity), new TypeToken<Map<String, Object>>(){}.getType());
        return new Pair<>(key, value);
    }

    @NotNull
    @Override
    public Iterator<TransferEntity> iterator() {
        return entities.iterator();
    }

    @Override
    public void forEach(Consumer<? super TransferEntity> action) {
        entities.forEach(action);
    }

    @Override
    public Spliterator<TransferEntity> spliterator() {
        return entities.spliterator();
    }

    @Override
    public String toString() {
        return "MessageChain{" +
                "entities=" + entities +
                '}';
    }

    static {
        HashMap<String, Class<? extends TransferEntity>> map = new HashMap<>();
        map.put(Anonymous.class.getSimpleName().toLowerCase(), Anonymous.class);
        map.put(At.class.getSimpleName().toLowerCase(), At.class);
        map.put(Dice.class.getSimpleName().toLowerCase(), Dice.class);
        map.put(Face.class.getSimpleName().toLowerCase(), Face.class);
        map.put(Forward.class.getSimpleName().toLowerCase(), Forward.class);
        map.put(Image.class.getSimpleName().toLowerCase(), Image.class);
        map.put(JSON.class.getSimpleName().toLowerCase(), JSON.class);
        map.put(Location.class.getSimpleName().toLowerCase(), Location.class);
        map.put(Poke.class.getSimpleName().toLowerCase(), Poke.class);
        map.put(Record.class.getSimpleName().toLowerCase(), Record.class);
        map.put(Redbag.class.getSimpleName().toLowerCase(), Redbag.class);
        map.put(Reply.class.getSimpleName().toLowerCase(), Reply.class);
        map.put(RPS.class.getSimpleName().toLowerCase(), RPS.class);
        map.put(Shake.class.getSimpleName().toLowerCase(), Shake.class);
        map.put(Share.class.getSimpleName().toLowerCase(), Share.class);
        map.put(Text.class.getSimpleName().toLowerCase(), Text.class);
        map.put(Video.class.getSimpleName().toLowerCase(), Video.class);
        map.put(XML.class.getSimpleName().toLowerCase(), XML.class);
        MAPPER = map;
    }

}
