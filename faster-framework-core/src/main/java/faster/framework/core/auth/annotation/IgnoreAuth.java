package faster.framework.core.auth.annotation;

import java.lang.annotation.*;

/**
 * 标记为通行权限
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface IgnoreAuth {
}
