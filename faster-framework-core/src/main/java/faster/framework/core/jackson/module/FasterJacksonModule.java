package faster.framework.core.jackson.module;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import faster.framework.core.jackson.codec.FasterDateDeserializer;
import faster.framework.core.jackson.codec.FasterDateTimeDeserializer;
import faster.framework.core.jackson.codec.FasterDateTimeSerializer;

import java.util.Date;

public class FasterJacksonModule extends Module {
    @Override
    public String getModuleName() {
        return FasterJacksonModule.class.getSimpleName();
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    @Override
    public void setupModule(SetupContext context) {
        context.addSerializers(this.serializers());
        context.addDeserializers(this.deserializers());
        context.addBeanSerializerModifier(FasterBeanSerializerModifier.INSTANCE);
        context.addBeanDeserializerModifier(FasterBeanDeserializerModifier.INSTANCE);
        context.insertAnnotationIntrospector(FasterAnnotationIntrospector.INSTANCE);
    }


    private Serializers serializers() {
        SimpleSerializers serializers = new SimpleSerializers();
        serializers.addSerializer(Long.class, ToStringSerializer.instance);
        serializers.addSerializer(long.class, ToStringSerializer.instance);
        serializers.addSerializer(DateTime.class, FasterDateTimeSerializer.instance);
        return serializers;
    }

    private Deserializers deserializers() {
        SimpleDeserializers deserializers = new SimpleDeserializers();
        deserializers.addDeserializer(Date.class, FasterDateDeserializer.instance);
        deserializers.addDeserializer(DateTime.class, FasterDateTimeDeserializer.instance);
        return deserializers;
    }
}
