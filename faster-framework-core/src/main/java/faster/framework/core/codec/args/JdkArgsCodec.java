package faster.framework.core.codec.args;


import cn.hutool.core.util.ObjectUtil;
import faster.framework.Faster;

/**
 * json编解码器
 *
 * @author lizhian
 * @date 2023年10月24日
 */
public class JdkArgsCodec implements ArgsCodec {
    @Override
    public byte[] serialize(Object[] value) {
        return ObjectUtil.serialize(value);
    }

    @Override
    public Object[] deserialize(byte[] bytes) {
        return Faster.JsonTyped.toObject(bytes);
    }
}
