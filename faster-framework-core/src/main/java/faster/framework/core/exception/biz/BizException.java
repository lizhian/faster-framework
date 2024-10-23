package faster.framework.core.exception.biz;

import faster.framework.core.domain.R;
import faster.framework.core.exception.BaseException;
import faster.framework.core.util.StrUtil;
import kotlin.jvm.functions.Function1;
import lombok.Setter;
import lombok.experimental.StandardException;


/**
 * 业务异常 不打印异常信息
 */
@StandardException
public class BizException extends BaseException {

    public static final Function1<String, BaseException> Creator = BizException::of;

    @Setter
    private Object expandData;

    public static BizException of(String message, Object... params) {
        return new BizException(StrUtil.format(message, params));
    }

    @Override
    public boolean isPrintStackTrace() {
        return false;
    }

    @Override
    public Object getHttpResponseJsonBody() {
        R<Object> failed = R.failed(this.getMessage());
        failed.setExpandData(this.expandData);
        return failed;
    }
}
