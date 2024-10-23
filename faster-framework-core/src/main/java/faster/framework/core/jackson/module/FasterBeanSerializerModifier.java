package faster.framework.core.jackson.module;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import faster.framework.core.enums.EnumCodec;
import faster.framework.core.jackson.codec.FasterEnumSerializer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FasterBeanSerializerModifier extends BeanSerializerModifier {
    public static final FasterBeanSerializerModifier INSTANCE = new FasterBeanSerializerModifier();
    private final static Map<Class<?>, JsonSerializer<?>> ENUM_SERIALIZERS = new ConcurrentHashMap<>();

    @Override
    public JsonSerializer<?> modifyEnumSerializer(SerializationConfig config, JavaType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        Class<?> clazz = valueType.getRawClass();
        return ENUM_SERIALIZERS.computeIfAbsent(clazz, this::createEnumSerializer);
    }

    private JsonSerializer<?> createEnumSerializer(Class<?> enumClass) {
        EnumCodec<?> enumCodec = EnumCodec.of(enumClass);
        return new FasterEnumSerializer<>(enumCodec);
    }


    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        return super.changeProperties(config, beanDesc, beanProperties);
    }
}
