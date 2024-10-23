package faster.framework.core.exception.biz;

import faster.framework.core.exception.BaseException;
import faster.framework.core.util.StrUtil;
import lombok.experimental.StandardException;

/**
 * 业务异常 打印异常信息
 */
@StandardException
public class BizErrorException extends BaseException {
    public static BizErrorException of(String message, Object... params) {
        return new BizErrorException(StrUtil.format(message, params));
    }
}
