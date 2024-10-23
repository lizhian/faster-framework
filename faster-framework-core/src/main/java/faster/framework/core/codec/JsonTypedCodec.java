package faster.framework.core.codec;


import faster.framework.Faster;
import lombok.Data;

/**
 * json编解码器
 *
 * @author lizhian
 * @date 2023年10月24日
 */
@Data
public class JsonTypedCodec<T> implements Codec<T> {
    @Override
    public byte[] serialize(T value) {
        return Faster.JsonTyped.toJsonBytes(value);
    }

    @Override
    public T deserialize(byte[] bytes) {
        return Faster.JsonTyped.toObject(bytes);
    }
}
