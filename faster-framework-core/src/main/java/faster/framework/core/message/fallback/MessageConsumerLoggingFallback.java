package faster.framework.core.message.fallback;

import faster.framework.core.codec.args.ArgsCodec;
import faster.framework.core.message.Message;
import faster.framework.core.message.enums.MessageChannel;
import faster.framework.core.message.enums.MessageProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageConsumerLoggingFallback implements MessageConsumerFallback {
    @Override
    public void onException(Message<Object[]> message, Throwable error, String source, String name, MessageChannel channel, MessageProvider provider, ArgsCodec codec) {
        log.error("消费 {} 消息发生异常: {} ", name, error.getMessage(), error);
    }
}
