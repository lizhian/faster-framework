package faster.framework.core.auth.annotation;

import cn.dev33.satoken.stp.StpUtil;
import faster.framework.Faster;
import faster.framework.core.auth.expand.AuthExpand;
import faster.framework.core.auth.expand.AuthExpandContext;
import faster.framework.core.auth.expand.AuthExpander;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AuthExpand(
        expandBy = MustPermission.Expander.class
)
public @interface MustPermission {
    String value();

    @Slf4j
    class Expander implements AuthExpander<MustPermission> {
        @Override
        public void doExpand(MustPermission mustPermission, AuthExpandContext context) {
            if (context.hasAnnotation(IgnoreAuth.class)) {
                return;
            }
            if (Faster.Auth.isSafeRequest()) {
                return;
            }
            Faster.Auth.mustLogin();
            if (Faster.Auth.isAdmin()) {
                return;
            }
            StpUtil.checkPermission(mustPermission.value());
        }
    }
}
