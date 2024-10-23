package faster.framework.core.message.fallback;

import faster.framework.core.codec.args.ArgsCodec;
import faster.framework.core.message.Message;
import faster.framework.core.message.enums.MessageChannel;
import faster.framework.core.message.enums.MessageProvider;

public interface MessageConsumerFallback {
    void onException(Message<Object[]> message, Throwable error, String source, String name, MessageChannel channel, MessageProvider provider, ArgsCodec codec);
}
