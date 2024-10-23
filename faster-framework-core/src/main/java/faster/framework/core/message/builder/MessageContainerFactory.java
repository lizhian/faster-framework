package faster.framework.core.message.builder;

import faster.framework.core.codec.Codec;
import faster.framework.core.message.MessageContainer;
import faster.framework.core.message.enums.MessageChannel;

public interface MessageContainerFactory {


    <T> MessageContainer<T> build(String source, String name, MessageChannel channel, Codec<T> codec);
}
