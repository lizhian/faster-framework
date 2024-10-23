package faster.framework.core.jackson.annotation;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import faster.framework.core.jackson.expland.ExpandJsonSerializer;
import faster.framework.core.jackson.expland.JsonExpandContext;
import faster.framework.core.util.SpringUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 序列化显示部门详情
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@JacksonAnnotationsInside
@JsonSerialize(using = ShowDeptDetail.Serializer.class)
public @interface ShowDeptDetail {

    String value() default "{}_DeptDetail";

    interface Bean {
        Object getDeptDetail(Object value);
    }

    class Serializer extends ExpandJsonSerializer<ShowDeptDetail> {
        private final Supplier<ShowDeptDetail.Bean> lazy = Suppliers.memoize(() -> SpringUtil.getBeanDefaultNull(ShowDeptDetail.Bean.class));

        @Override
        protected void doExpand(@NotNull ShowDeptDetail annotation, @NotNull JsonExpandContext context) {
            String key = StrUtil.format(annotation.value(), context.getPropertyName());
            Object detail = null;
            ShowDeptDetail.Bean bean = this.lazy.get();
            if (bean != null) {
                detail = bean.getDeptDetail(context.getPropertyValue());
            }
            context.write(key, detail);
        }
    }
}
