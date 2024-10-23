package faster.framework.core.jackson.annotation;

import faster.framework.core.jackson.expland.JsonExpand;
import faster.framework.core.jackson.expland.JsonExpandContext;
import faster.framework.core.jackson.expland.JsonExpander;

import java.lang.annotation.*;

/**
 * 序列化增加 别名 属性
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@JsonExpand(expandBy = Alias.Expander.class)
public @interface Alias {
    /**
     * 别名
     */
    String[] value();

    /**
     * 反序列化的时候优先从别名获取值
     */
    boolean deserializeFirst() default false;

    class Expander implements JsonExpander<Alias> {

        @Override
        public void doExpand(Alias annot, JsonExpandContext context) {
            Object value = context.getPropertyValue();
            for (String key : annot.value()) {
                context.write(key, value);
            }
        }
    }
}
