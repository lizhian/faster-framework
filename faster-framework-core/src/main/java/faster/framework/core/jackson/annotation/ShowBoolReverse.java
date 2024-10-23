package faster.framework.core.jackson.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import faster.framework.core.jackson.expland.ExpandJsonSerializer;
import faster.framework.core.jackson.expland.JsonExpandContext;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 序列化显示显示布尔值的相反值
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@JacksonAnnotationsInside
@JsonSerialize(using = ShowBoolReverse.Serializer.class)
public @interface ShowBoolReverse {
    String value();

    class Serializer extends ExpandJsonSerializer<ShowBoolReverse> {
        @Override
        protected void doExpand(@NotNull ShowBoolReverse annotation, @NotNull JsonExpandContext context) {
            String key = annotation.value();
            Object value = context.getPropertyValue();
            if (value == null) {
                context.write(key, null);
                return;
            }
            if (Boolean.TRUE.equals(value)) {
                context.write(key, false);
                return;
            }
            if (Boolean.FALSE.equals(value)) {
                context.write(key, true);
            }
        }
    }
}
