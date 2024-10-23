package faster.framework.core.jackson.expland;

import cn.hutool.core.util.ReflectUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import faster.framework.core.util.ClassUtil;
import lombok.AccessLevel;
import lombok.Setter;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * 拓展json序列化
 *
 * @author lizhian
 * @date 2024年02月12日
 */
public abstract class ExpandJsonSerializer<A extends Annotation>
        extends JsonSerializer<Object> implements ContextualSerializer {
    @Setter(AccessLevel.PRIVATE)
    private A annotation;
    @Setter(AccessLevel.PRIVATE)
    private BeanProperty property;

    protected abstract void doExpand(@Nonnull A annotation, @Nonnull JsonExpandContext context);

    /**
     * 序列化方法，将Java对象转换为JSON字符串
     *
     * @param value       要序列化的Java对象
     * @param generator   JsonGenerator对象，用于生成JSON字符串
     * @param serializers SerializerProvider对象，用于处理序列化过程中的异常
     * @throws IOException 如果序列化过程中发生IO异常
     */
    @Override
    public void serialize(Object value, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        generator.writeObject(value);
        if (this.annotation == null || this.property == null) {
            return;
        }
        // 构建JsonExpandContext对象，用于存储序列化过程中的相关信息
        JsonExpandContext context = JsonExpandContext.builder()
                .propertyName(this.property.getName())
                .propertyType(this.property.getType())
                .propertyValue(value)
                .generator(generator)
                .provider(serializers)
                .build();
        // 调用doExpand方法，执行扩展操作
        this.doExpand(this.annotation, context);
    }

    /**
     * 创建上下文，用于生成特定的JsonSerializer对象
     *
     * @param prov     SerializerProvider对象，用于处理序列化过程中的异常
     * @param property BeanProperty对象，表示要序列化的属性
     * @return 返回一个JsonSerializer对象，用于序列化特定的属性
     */
    @Override
    @SuppressWarnings("unchecked")
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        // 获取注解的类型
        Class<A> annotationClass = (Class<A>) ClassUtil.getTypeArgument(this.getClass());
        // 获取属性上的注解
        A annotation = property.getAnnotation(annotationClass);
        if (annotation == null) {
            return this;
        }
        // 创建一个新的JsonSerializer对象
        ExpandJsonSerializer<A> newSerializer = ReflectUtil.newInstance(this.getClass());
        newSerializer.setAnnotation(annotation);
        newSerializer.setProperty(property);
        return newSerializer;
    }
}
