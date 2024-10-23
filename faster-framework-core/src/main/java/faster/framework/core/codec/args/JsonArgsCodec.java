package faster.framework.core.codec.args;


import faster.framework.Faster;

/**
 * json编解码器
 *
 * @author lizhian
 * @date 2023年10月24日
 */
public class JsonArgsCodec implements ArgsCodec {
    @Override
    public byte[] serialize(Object[] value) {
        return Faster.JsonTyped.toJsonBytes(value);
    }

    @Override
    public Object[] deserialize(byte[] bytes) {
        return Faster.JsonTyped.toObject(bytes);
    }
}
