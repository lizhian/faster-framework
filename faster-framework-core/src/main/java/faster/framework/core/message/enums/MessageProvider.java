package faster.framework.core.message.enums;


import faster.framework.core.message.builder.IKafkaMessageContainerFactory;
import faster.framework.core.message.builder.IRabbitMessageContainerFactory;
import faster.framework.core.message.builder.IRedisMessageContainerFactory;
import faster.framework.core.message.builder.MessageContainerFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 消息提供程序
 *
 * @author lizhian
 * @date 2023年11月24日
 */
@RequiredArgsConstructor
@Getter
public enum MessageProvider {
    redis(IRedisMessageContainerFactory.class), kafka(IKafkaMessageContainerFactory.class), rabbit(IRabbitMessageContainerFactory.class);
    private final Class<? extends MessageContainerFactory> factoryClass;
}
