package faster.framework.core.auth.expand;

import java.lang.annotation.Annotation;

/**
 * 认证拓展器
 */
public interface AuthExpander<A extends Annotation> {
    void doExpand(A authAnnotation, AuthExpandContext context);
}
