package faster.framework.core.jackson.codec;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Date;

@Slf4j
@JacksonStdImpl
public class FasterDateDeserializer extends StdScalarDeserializer<Date> {

    public final static FasterDateDeserializer instance = new FasterDateDeserializer();

    protected FasterDateDeserializer() {
        super(Date.class);
    }


    @Override
    public Date deserialize(JsonParser p, DeserializationContext context) throws IOException {
        String value = p.getValueAsString();
        if (StrUtil.isBlank(value)) {
            return null;
        }
        if (NumberUtil.isLong(value) && value.length() == 13) {
            return new Date(Long.parseLong(value));
        }
        if (NumberUtil.isLong(value) && value.length() == 10) {
            return new Date(Long.parseLong(value) * 1000);
        }
        return DateUtil.parse(value).toJdkDate();
    }
}
