package faster.framework.core.innerRequest;

import cn.hutool.core.util.StrUtil;
import faster.framework.core.exception.BaseException;
import lombok.experimental.StandardException;

@StandardException
public class InnerRequestException extends BaseException {


    public static InnerRequestException of(String template, Object... params) {
        return new InnerRequestException(StrUtil.format(template, params));
    }


}
