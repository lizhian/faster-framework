package faster.framework.core.auth.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * 简单权限信息:权限编码+角色编码
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Builder(toBuilder = true)
public final class SimpleAuthDetail implements AuthDetail {
    private List<String> permissions;
    private List<String> roles;
}
