package faster.framework.core.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 默认属性
 *
 * @author lizhian
 * @date 2023年07月20日
 */
@Data
@Builder
public class DefaultProperty implements Serializable {
    /**
     * 注释
     */
    private final String comment;
    /**
     * 键
     */
    private final String key;
    /**
     * 值
     */
    private final String value;
}
