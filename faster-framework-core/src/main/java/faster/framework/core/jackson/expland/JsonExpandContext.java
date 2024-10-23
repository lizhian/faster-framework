package faster.framework.core.jackson.expland;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

@Data
@FieldNameConstants
@SuperBuilder(toBuilder = true)
public class JsonExpandContext {
    private final String propertyName;
    private final Object propertyValue;
    private final JavaType propertyType;
    private final JsonGenerator generator;
    private final SerializerProvider provider;

    @SneakyThrows
    public void write(String key, Object value) {
        if (StrUtil.isBlank(key)) {
            return;
        }
        if (value == null) {
            this.generator.writeNullField(key);
        } else {
            this.generator.writeObjectField(key, value);
        }
    }

}
