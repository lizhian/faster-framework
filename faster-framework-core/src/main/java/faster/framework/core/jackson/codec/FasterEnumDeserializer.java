package faster.framework.core.jackson.codec;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import faster.framework.core.enums.EnumCodec;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class FasterEnumDeserializer<E extends Enum<E>> extends JsonDeserializer<E> {
    private final EnumCodec<E> enumCodec;

    @Override
    public E deserialize(JsonParser p, DeserializationContext context) throws IOException {
        String value = p.getValueAsString();
        return this.enumCodec.getEnumInstance(value);
    }


}