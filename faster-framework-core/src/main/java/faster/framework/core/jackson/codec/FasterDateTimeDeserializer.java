package faster.framework.core.jackson.codec;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@Getter
public class FasterDateTimeDeserializer extends StdScalarDeserializer<DateTime> {
    public final static FasterDateTimeDeserializer instance = new FasterDateTimeDeserializer();

    private FasterDateTimeDeserializer() {
        super(DateTime.class);
    }

    @Override
    public DateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = p.getValueAsString();
        if (StrUtil.isBlank(value)) {
            return null;
        }
        if (NumberUtil.isLong(value) && value.length() == 13) {
            return DateUtil.date(Long.parseLong(value));
        }
        if (NumberUtil.isLong(value) && value.length() == 10) {
            return DateUtil.date(Long.parseLong(value) * 1000);
        }
        return DateUtil.parse(value);
    }
}
