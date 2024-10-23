package faster.framework.core.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import faster.framework.core.exception.biz.BizException;
import faster.framework.core.validation.UpdateGroup;
import faster.framework.core.validation.ValidErrorDetail;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ValidUtil {

    private static final Validator validator;

    static {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    public static void validOnUpdate(Object object) {
        ValidUtil.valid(object, UpdateGroup.class, Default.class);
    }

    /**
     * 校验参数
     *
     * @param object
     */
    public static void valid(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> errors = validator.validate(object, groups);
        if (CollUtil.isEmpty(errors)) {
            return;
        }
        List<ValidErrorDetail> errorDetails = errors.stream()
                .map(ValidErrorDetail::from)
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(errorDetails)) {
            return;
        }

        List<String> batchMessage = errorDetails.stream()
                .map(ValidErrorDetail::getMergeMessage)
                .collect(Collectors.toList());
        List<String> properties = errorDetails.stream()
                .map(ValidErrorDetail::getProperty)
                .distinct()
                .collect(Collectors.toList());
        String message = StrUtil.join(StrPool.COMMA, batchMessage);
        log.error("参数校验异常,属性:{},错误信息:{}", properties, message);
        Map<String, Object> expandData = MapUtil
                .<String, Object>builder("batchMessage", batchMessage)
                .put("errorDetails", errorDetails)
                .build();
        BizException bizException = BizException.of(message);
        bizException.setExpandData(expandData);
        throw bizException;
    }


    public static void mustTrue(Boolean bool, String message) {
        if (Boolean.TRUE.equals(bool)) {
            return;
        }
        throw BizException.of(message);
    }

    // value不能为空字符
    public static void notBlank(String value, String message) {
        if (StrUtil.isBlank(value)) {
            throw BizException.of(message);
        }
    }

    // value长度必须为length
    public static void strLength(String value, int length, String message) {
        if (StrUtil.isNotBlank(value) && value.length() == length) {
            return;
        }
        throw BizException.of(message);
    }

    // value必须为数字类型
    public static void isNumber(String value, String message) {
        if (NumberUtil.isNumber(value)) {
            return;
        }
        throw BizException.of(message);
    }

    // value不能为空
    public static void notNull(@Nullable Object value, String message) {
        if (value == null) {
            throw BizException.of(message);
        }
        if (value instanceof String && StrUtil.isBlank((String) value)) {
            throw BizException.of(message);
        }
        if (value instanceof Collection<?> && CollUtil.isEmpty((Collection<?>) value)) {
            throw BizException.of(message);
        }
    }

    // value必须为空
    public static void isNull(Object value, String message) {
        if (value != null) {
            throw BizException.of(message);
        }
    }

    // value等于target  就抛出异常
    public static void notEquals(Object value, Object target, String message) {
        if (ObjectUtil.equals(value, target)) {
            throw BizException.of(message);
        }
    }

    public static void equals(Object value, Object target, String message) {
        if (ObjectUtil.equals(value, target)) {
            return;
        }
        throw BizException.of(message);

    }

    // 字符长度必须在length之内
    public static void strBeyondLength(String value, int length, String message) {
        if (StrUtil.isNotBlank(value) && value.length() < length) {
            return;
        }
        throw BizException.of(message);
    }

    public static void notContains(String value, String target, String message) {
        if (StrUtil.isBlank(value) || StrUtil.isBlank(target)) {
            return;
        }
        if (value.contains(target)) {
            throw BizException.of(message);
        }
    }


    public static void mustFalse(Boolean bool, String message) {
        if (Boolean.FALSE.equals(bool)) {
            return;
        }
        throw BizException.of(message);
    }
}
