package faster.framework.core.jackson.annotation;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import faster.framework.core.dict.DictDetail;
import faster.framework.core.jackson.expland.ExpandJsonSerializer;
import faster.framework.core.jackson.expland.JsonExpandContext;
import faster.framework.core.util.SpringUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 序列化显示字典详情
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@JacksonAnnotationsInside
@JsonSerialize(using = ShowDictDetail.Serializer.class)
public @interface ShowDictDetail {

    String value() default "";

    String property() default "{}_DictDetail";


    interface Bean {
        DictDetail getDictDetail(Object value, JavaType type, ShowDictDetail annotation);
    }

    class Serializer extends ExpandJsonSerializer<ShowDictDetail> {
        private final Supplier<ShowDictDetail.Bean> lazy = Suppliers.memoize(() -> SpringUtil.getBeanDefaultNull(ShowDictDetail.Bean.class));

        @Override
        protected void doExpand(@NotNull ShowDictDetail annotation, @NotNull JsonExpandContext context) {
            String key = StrUtil.format(annotation.value(), context.getPropertyName());
            Object detail = null;
            ShowDictDetail.Bean bean = this.lazy.get();
            if (bean != null) {
                detail = bean.getDictDetail(context.getPropertyValue(), context.getPropertyType(), annotation);
            }
            context.write(key, detail);
        }
    }
}
