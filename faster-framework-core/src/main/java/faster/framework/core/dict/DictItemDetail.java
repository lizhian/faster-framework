package faster.framework.core.dict;

import faster.framework.core.jackson.annotation.ShowBoolReverse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Builder(toBuilder = true)
public class DictItemDetail implements Serializable {
    @Schema(description = "值")
    private String value;

    @Schema(description = "标签")
    private String label;

    @Schema(description = "顺序")
    private Integer sort;

    @Schema(description = "样式")
    private String style;

    @Schema(description = "是否启用")
    @ShowBoolReverse("disabled")
    private Boolean enable;

    @Schema(description = "备注")
    private String remark;
}
