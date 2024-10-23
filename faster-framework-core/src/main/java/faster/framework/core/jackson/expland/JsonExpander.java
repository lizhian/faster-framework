package faster.framework.core.jackson.expland;

import java.lang.annotation.Annotation;

/**
 * json拓展器
 */
public interface JsonExpander<A extends Annotation> {
    void doExpand(A annot, JsonExpandContext context);
}
