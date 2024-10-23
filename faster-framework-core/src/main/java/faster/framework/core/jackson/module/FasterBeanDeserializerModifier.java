package faster.framework.core.jackson.module;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import faster.framework.core.enums.EnumCodec;
import faster.framework.core.jackson.codec.FasterEnumDeserializer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FasterBeanDeserializerModifier extends BeanDeserializerModifier {
    public static final FasterBeanDeserializerModifier INSTANCE = new FasterBeanDeserializerModifier();
    private final static Map<Class<?>, JsonDeserializer<?>> ENUM_DESERIALIZERS = new ConcurrentHashMap<>();


    @Override
    public JsonDeserializer<?> modifyEnumDeserializer(DeserializationConfig config,
                                                      JavaType type, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
        Class<?> clazz = type.getRawClass();
        return ENUM_DESERIALIZERS.computeIfAbsent(clazz, this::createEnumDeserializer);
    }

    private JsonDeserializer<?> createEnumDeserializer(Class<?> enumClass) {
        EnumCodec<?> enumCodec = EnumCodec.of(enumClass);
        return new FasterEnumDeserializer<>(enumCodec);
    }
}
