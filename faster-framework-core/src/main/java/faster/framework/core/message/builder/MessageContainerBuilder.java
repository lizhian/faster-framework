package faster.framework.core.message.builder;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import faster.framework.core.codec.Codec;
import faster.framework.core.exception.biz.FrameworkException;
import faster.framework.core.message.LazyMessageContainer;
import faster.framework.core.message.MessageContainer;
import faster.framework.core.message.enums.MessageChannel;
import faster.framework.core.message.enums.MessageProvider;
import faster.framework.core.util.SpringUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageContainerBuilder<T> {
    /**
     * 消息源
     */
    private String source;
    /**
     * 队列名称/主题名称
     */
    private String name;
    /**
     * 消息渠道,队列/主题
     */
    private MessageChannel channel;
    /**
     * 消息提供商,redis/kafka/rabbit
     */
    private MessageProvider provider;
    /**
     * 编解码器
     */
    private Codec<T> codec;

    /**
     * 消息源
     */
    public MessageContainerBuilder<T> source(String source) {
        this.source = source;
        return this;
    }

    public MessageContainerBuilder<T> name(@NonNull String name) {
        this.name = name;
        return this;
    }

    public MessageContainerBuilder<T> as(MessageChannel channel) {
        this.channel = channel;
        return this;
    }

    public MessageContainerBuilder<T> asQueue() {
        this.channel = MessageChannel.queue;
        return this;
    }

    public MessageContainerBuilder<T> asTopic() {
        this.channel = MessageChannel.topic;
        return this;
    }

    public MessageContainerBuilder<T> use(MessageProvider provider) {
        this.provider = provider;
        return this;
    }

    public MessageContainerBuilder<T> useRedis() {
        this.provider = MessageProvider.redis;
        return this;
    }

    public MessageContainerBuilder<T> useKafka() {
        this.provider = MessageProvider.kafka;
        return this;
    }

    public MessageContainerBuilder<T> useRabbit() {
        this.provider = MessageProvider.rabbit;
        return this;
    }

    /**
     * 编解码器
     */
    public MessageContainerBuilder<T> codec(@NonNull Codec<T> codec) {
        this.codec = codec;
        return this;
    }

    /**
     * 构建缓存容器
     */
    public MessageContainer<T> build() {
        Supplier<MessageContainer<T>> delegate = Suppliers.memoize(() -> {
            Class<? extends MessageContainerFactory> factoryClass = this.provider.getFactoryClass();
            MessageContainerFactory factory = SpringUtil.getAndCache(factoryClass);
            if (factory == null) {
                throw FrameworkException.of("[{}}]未找到实现", factoryClass.getName());
            }
            MessageContainer<T> container = factory.build(this.source, this.name, this.channel, this.codec);
            return container;
        });
        return new LazyMessageContainer<>(delegate);

    }


}
