package faster.framework.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import faster.framework.Faster;
import faster.framework.core.jackson.annotation.Alias;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * 响应信息
 */
@Data
@NoArgsConstructor
@FieldNameConstants
@AllArgsConstructor
@Builder(toBuilder = true)
public class R<T> implements Serializable {

    @Schema(description = "响应编码")
    private int code;

    @Schema(description = "响应消息")
    @Alias("msg")
    private String message;

    @Schema(description = "响应数据")
    private T data;

    @Schema(description = "链路标识", hidden = true)
    private String traceId;

    @Schema(description = "拓展属性", hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object expandData;


    public static <T> R<T> of(int code, String message, T data) {
        return R.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .traceId(Faster.TraceId.get())
                .build();
    }

    public static <T> R<T> of(int code, String message) {
        return of(code, message, null);
    }

    public static <T> R<T> ok() {
        return of(200, "操作成功");
    }

    public static <T> R<T> success() {
        return of(200, "操作成功");
    }

    public static <T> R<T> ok(T data) {
        return of(200, "操作成功", data);
    }
    public static <T> R<T> success(T data) {
        return of(200, "操作成功", data);
    }

    public static <T> R<T> failed() {
        return of(400, "操作失败");
    }
    public static <T> R<T> failed(String message) {
        return of(400, message);
    }
    public static <T> R<T> failed(int code, String message) {
        return of(code, message);
    }
}
