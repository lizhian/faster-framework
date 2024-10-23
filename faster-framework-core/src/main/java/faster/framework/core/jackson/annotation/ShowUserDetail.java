package faster.framework.core.jackson.annotation;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Supplier;
import faster.framework.core.jackson.expland.ExpandJsonSerializer;
import faster.framework.core.jackson.expland.JsonExpandContext;
import faster.framework.core.util.SpringUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 序列化显示用户详情
 */

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@JacksonAnnotationsInside
@JsonSerialize(using = ShowUserDetail.Serializer.class)
public @interface ShowUserDetail {

    String value() default "{}_UserDetail";

    interface Bean {
        Object getUserDetail(Object value);
    }

    class Serializer extends ExpandJsonSerializer<ShowUserDetail> {
        private final Supplier<Bean> lazy = SpringUtil.lazyBean(ShowUserDetail.Bean.class, true);

        @Override
        protected void doExpand(@NotNull ShowUserDetail annotation, @NotNull JsonExpandContext context) {
            String key = StrUtil.format(annotation.value(), context.getPropertyName());
            Object detail = null;
            Bean bean = this.lazy.get();
            if (bean != null) {
                detail = bean.getUserDetail(context.getPropertyValue());
            }
            context.write(key, detail);
        }
    }
}
