package faster.framework.core.message;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import faster.framework.Faster;
import faster.framework.core.util.StrUtil;
import lombok.Data;

import java.util.Map;

@Data
public class Message<T> {
    public static final String x_trace_id = Faster.TraceId.x_trace_id;
    public static final String x_send_time = "x-send-time";
    public static final String x_sender_account = "x-sender-account";

    private final Map<String, String> header;
    private final T body;

    public String getTrackId() {
        return this.header.get(x_trace_id);
    }

    public DateTime getSendTime() {
        String value = this.header.get(x_send_time);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return DateUtil.parse(value);
    }

    public boolean isEmpty() {
        return ObjectUtil.isEmpty(this.body);
    }

    public boolean isNotEmpty() {
        return ObjectUtil.isNotEmpty(this.body);
    }

}
