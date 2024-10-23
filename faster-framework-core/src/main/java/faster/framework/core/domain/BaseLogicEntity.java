package faster.framework.core.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tangzc.mpe.autotable.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 * 逻辑删除基础实体类
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
public abstract class BaseLogicEntity extends BaseEntity {

    @Column(comment = "逻辑删除标识")
    @Schema(hidden = true)
    @TableLogic
    private Boolean deleted;

    @JsonIgnore
    public boolean isLogicDeleted() {
        return this.deleted != null && this.deleted;
    }

    @Override
    public void copyBaseField(BaseEntity other) {
        super.copyBaseField(other);
        if (other instanceof BaseLogicEntity) {
            BaseLogicEntity otherBaseLogicEntity = (BaseLogicEntity) other;
            this.setDeleted(otherBaseLogicEntity.getDeleted());
        }
    }

    @Override
    public void beforeInsert() {
        super.beforeInsert();
        if (this.deleted == null) {
            this.deleted = false;
        }
    }
}
