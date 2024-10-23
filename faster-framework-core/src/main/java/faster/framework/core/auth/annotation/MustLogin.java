package faster.framework.core.auth.annotation;

import faster.framework.Faster;
import faster.framework.core.auth.expand.AuthExpand;
import faster.framework.core.auth.expand.AuthExpandContext;
import faster.framework.core.auth.expand.AuthExpander;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AuthExpand(
        expandBy = MustLogin.Expander.class
)
public @interface MustLogin {

    class Expander implements AuthExpander<MustLogin> {
        @Override
        public void doExpand(MustLogin mustLogin, AuthExpandContext context) {
            if (context.hasAnnotation(IgnoreAuth.class)) {
                return;
            }
            if (Faster.Auth.isSafeRequest()) {
                return;
            }
            Faster.Auth.mustLogin();
        }
    }
}
