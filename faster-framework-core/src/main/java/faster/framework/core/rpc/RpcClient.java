package faster.framework.core.rpc;

import faster.framework.core.rpc.enums.HostType;

import java.lang.annotation.*;

/**
 * 内部请求服务名称
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcClient {
    String PATH = "/faster/rpc";

    String host();

    HostType type();

    String path() default RpcClient.PATH;
}
