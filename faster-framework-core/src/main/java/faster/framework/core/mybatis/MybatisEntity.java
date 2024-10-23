package faster.framework.core.mybatis;

import java.util.Map;

/**
 * 实体数据操作定义
 */
public interface MybatisEntity {

    /**
     * 数据新增前操作
     */
    default void beforeInsert() {
    }

    /**
     * 数据更新前操作
     */
    default void beforeUpdate() {
    }

    /**
     * lambda更新前操作
     */
    default void beforeLambdaUpdate(Map<String, Object> updateSets) {
    }


    /**
     * 数据查出后操作
     */
    default void afterQuery() {

    }

}
