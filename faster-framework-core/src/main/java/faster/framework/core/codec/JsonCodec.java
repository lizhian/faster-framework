package faster.framework.core.codec;


import com.fasterxml.jackson.core.type.TypeReference;
import faster.framework.Faster;
import lombok.Data;

/**
 * json编解码器
 *
 * @author lizhian
 * @date 2023年10月24日
 */
@Data
public class JsonCodec<T> implements Codec<T> {

    private final Class<T> clazz;
    private final TypeReference<T> typeReference;
    private final boolean typed;


    public JsonCodec(Class<T> clazz, boolean typed) {
        this.clazz = clazz;
        this.typeReference = null;
        this.typed = typed;
    }

    public JsonCodec(TypeReference<T> typeReference, boolean typed) {
        this.clazz = null;
        this.typeReference = typeReference;
        this.typed = typed;
    }


    @Override
    public byte[] serialize(T value) {
        return this.typed
                ? Faster.JsonTyped.toJsonBytes(value)
                : Faster.Json.toJsonBytes(value);
    }

    @Override
    public T deserialize(byte[] bytes) {
        if (this.typed) {
            return Faster.JsonTyped.toObject(bytes);
        }
        if (this.typeReference != null) {
            return Faster.Json.toBean(bytes, this.typeReference);
        } else {
            return Faster.Json.toBean(bytes, this.clazz);
        }
    }
}
