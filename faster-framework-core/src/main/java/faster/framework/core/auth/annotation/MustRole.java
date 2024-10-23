package faster.framework.core.auth.annotation;

import cn.dev33.satoken.stp.StpUtil;
import faster.framework.Faster;
import faster.framework.core.auth.expand.AuthExpand;
import faster.framework.core.auth.expand.AuthExpandContext;
import faster.framework.core.auth.expand.AuthExpander;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AuthExpand(
        expandBy = MustRole.Expander.class
)
public @interface MustRole {
    String value();

    class Expander implements AuthExpander<MustRole> {
        @Override
        public void doExpand(MustRole mustRole, AuthExpandContext context) {
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
            StpUtil.checkRole(mustRole.value());
        }
    }
}
