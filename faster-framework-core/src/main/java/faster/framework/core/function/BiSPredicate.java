package faster.framework.core.function;

import java.io.Serializable;
import java.util.function.BiPredicate;

@FunctionalInterface
public interface BiSPredicate<T, U> extends BiPredicate<T, U>, Serializable {
}
