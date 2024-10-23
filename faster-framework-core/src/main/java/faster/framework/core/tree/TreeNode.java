package faster.framework.core.tree;

import com.fasterxml.jackson.annotation.JsonInclude;
import faster.framework.core.jackson.annotation.Alias;
import faster.framework.core.jackson.annotation.ShowBoolReverse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 树节点
 */
@Data
public class TreeNode<T> implements Serializable {
    @Schema(description = "树节点主键")
    private Serializable key;

    @Schema(description = "树节点名称")
    @Alias({"label", "title"})
    private String name;

    @Schema(description = "树节点数据")
    private T data;

    @Schema(description = "树父节点主键")
    private Serializable parentKey;

    @Schema(description = "祖先列表")
    private List<Serializable> ancestors;

    @ShowBoolReverse("isLeafNode")
    @Schema(description = "是否有子节点")
    private Boolean hasChildren;

    @ShowBoolReverse("disabled")
    @Schema(description = "是否启用")
    private Boolean enable;

    @Schema(description = "子节点集合")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeNode<T>> children;

}
