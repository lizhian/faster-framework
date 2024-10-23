package faster.framework.core.auth.annotation;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import faster.framework.Faster;
import faster.framework.core.auth.expand.AuthExpand;
import faster.framework.core.auth.expand.AuthExpandContext;
import faster.framework.core.auth.expand.AuthExpander;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.*;
import java.util.List;

/**
 * 基础权限检验器
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AuthExpand(
        expandBy = BaseAuth.Expander.class
)
public @interface BaseAuth {
    String value();

    class Expander implements AuthExpander<BaseAuth> {
        @Override
        public void doExpand(BaseAuth baseAuth, AuthExpandContext context) {
            if (context.hasAnnotation(IgnoreAuth.class)) {
                return;
            }
            if (Faster.Auth.isSafeRequest()) {
                return;
            }
            List<Annotation> otherAuthAnnotation = context.getOtherAuthAnnotations();
            if (CollUtil.isNotEmpty(otherAuthAnnotation)) {
                return;
            }
            Faster.Auth.mustLogin();
            if (Faster.Auth.isAdmin()) {
                return;
            }
            String permission = baseAuth.value() + ":" + this.getMethodType(context);
            StpUtil.checkPermission(permission);
        }

        private String getMethodType(AuthExpandContext context) {
            HttpServletRequest request = context.getRequest();
            if (context.hasAnnotation(QueryPermission.class) || request.getMethod().equalsIgnoreCase("get")) {
                return "query";
            }
            if (context.hasAnnotation(DeletePermission.class) || request.getMethod().equalsIgnoreCase("delete")) {
                return "delete";
            }
            return "edit";
        }
    }
}
