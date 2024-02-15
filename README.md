# perpetua-sdk-for-java

> 注意：您使用的 sdk 版本与 Perpetua 版本号应当一一对应

Perpetua 的通信 sdk，java 实现

## 导入

### Maven

```xml
<dependency>
  <groupId>com.illtamer.perpetua.sdk</groupId>
  <artifactId>perpetua-sdk</artifactId>
  <version>${project.version}</version>
</dependency>
```

## 快速入门

### 连接

```
    OneBotConnection.start("127.0.0.1", 8080, null, event -> {
        if (event instanceof HeartbeatEvent) return;
        if (event instanceof MessageEvent) {
            System.out.println(event);
            System.out.println(((MessageEvent) event).getMessage().getMessageChain());
        }
    });
```

### 构建消息

```
    Message message = MessageBuilder.json()
            .text("Hi", false)
            .face(FaceType._159)
            .text("这是一条自动换行消息")
            .build();
```

### 筛选消息

```
    Message message = ((MessageEvent) event).getMessage();
    for (TransferEntity entity : message.getMessageChain()) {
        if (entity instanceof At) {
            System.out.printf("QQ: %s, 被@", ((At) entity).getQq());
        }
    }
```

### 调用 API

- 同步

    ```
        OpenAPIHandling.sendMessage("hi", 765743073L);
    ```
  
  或者

  ```
      Response<Map<String, Object>> response = new PrivateMsgSendHandler()
              .setUserId(userId)
              .setMessage(message)
              .request();
      long messageId = (long) response.getData().get("message_id");
  ```

- 异步

  ```
      Supplier<Response<Object>> supplier = new GroupSetCardHandler()
              .setGroupId(863522624L)
              .setUserId(765743073L)
              .setCard("新的群名片昵称")
              .requestAsync();
      // new thread
      System.out.println(supplier.get());
  ```

### SpringBoot

```
public class OneBotAutoConfiguration implements ApplicationRunner {

    private final BotProperties botProperties;
    private final Consumer<Event> eventConsumer;

    public OneBotAutoConfiguration(
            BotProperties botProperties,
            ApplicationContext context
    ) {
        this.botProperties = botProperties;
        this.eventConsumer = context::publishEvent;
    }

    @Async
    @Override
    public void run(ApplicationArguments args) throws InterruptedException {
        OneBotConnection.start(
            botProperties.getHost(), 
            botProperties.getHttpUri(),
            botProperties.getAuthorization(), 
            eventConsumer
        );
    }

}
```