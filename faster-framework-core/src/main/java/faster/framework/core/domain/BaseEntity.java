package faster.framework.core.domain;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tangzc.mpe.autotable.annotation.Column;
import faster.framework.Faster;
import faster.framework.core.jackson.annotation.ShowUserDetail;
import faster.framework.core.mybatis.MybatisEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 基础实体类
 */
@Getter
@Setter
@FieldNameConstants
public abstract class BaseEntity implements Serializable, MybatisEntity {

    /**
     * 创建人
     */
    @Column(comment = "创建人")
    @Schema(hidden = true)
    @ShowUserDetail
    private String createBy;

    /**
     * 创建时间
     */
    @Column(comment = "创建时间")
    @Schema(hidden = true)
    private Date createTime;

    /**
     * 最后更新人
     */
    @ShowUserDetail
    @Column(comment = "最后更新人")
    @Schema(hidden = true)
    private String updateBy;

    /**
     * 最后更新时间
     */
    @Column(comment = "最后更新时间")
    @Schema(hidden = true)
    private Date updateTime;


    @JsonIgnore
    public DateTime getCreateDateTime() {
        return this.createTime == null ? null : DateTime.of(this.createTime);
    }

    @JsonIgnore
    public DateTime getUpdateDateTime() {
        return this.updateTime == null ? null : DateTime.of(this.updateTime);
    }

    public void copyBaseField(BaseEntity other) {
        if (other == null) {
            return;
        }
        this.setCreateBy(other.getCreateBy());
        this.setCreateTime(other.getCreateTime());
        this.setUpdateBy(other.getUpdateBy());
        this.setUpdateTime(other.getUpdateTime());
    }

    @Override
    public void beforeInsert() {
        String account = Faster.Auth.getAccount("unknown");
        Date now = DateTime.now().setField(DateField.MILLISECOND, 0);
        this.createBy = account;
        this.updateBy = account;
        this.createTime = now;
        this.updateTime = now;
    }

    @Override
    public void beforeUpdate() {
        String account = Faster.Auth.getAccount("unknown");
        Date now = DateTime.now().setField(DateField.MILLISECOND, 0);
        this.updateBy = account;
        this.updateTime = now;
    }

    @Override
    public void beforeLambdaUpdate(Map<String, Object> updateSets) {
        String account = Faster.Auth.getAccount("unknown");
        Date now = DateTime.now().setField(DateField.MILLISECOND, 0);
        updateSets.put(Fields.updateBy, account);
        updateSets.put(Fields.updateTime, now);
    }

    @Override
    public void afterQuery() {
    }
}
