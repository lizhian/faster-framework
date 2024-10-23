package faster.framework.core.rpc;

import cn.hutool.aop.ProxyUtil;
import cn.hutool.core.annotation.AnnotationUtil;
import faster.framework.core.exception.biz.FrameworkException;
import faster.framework.core.util.SpringUtil;
import lombok.SneakyThrows;

import java.lang.reflect.Method;

public class FasterRPC {

    public static <T> T of(Class<T> clazz) {
        RpcClient rpcClient = AnnotationUtil.getAnnotation(clazz, RpcClient.class);
        if (rpcClient == null) {
            throw FrameworkException.of("[{}]未找到[@RpcClient]", clazz.getSimpleName());
        }
        ;
        return ProxyUtil.newProxyInstance(FasterRPC::invoke, clazz);
    }

    @SneakyThrows
    private static Object invoke(Object proxy, Method method, Object[] args) {

        FasterRPC.Client client = SpringUtil.getAndCache(FasterRPC.Client.class);
        if (client != null) {
            return client.invoke(proxy, method, args);
        }
        throw FrameworkException.of("未找到[FasterRPC.Client]实现类,无法执行代理方法:{}", method);
    }

    public interface Client {
        Object invoke(Object proxy, Method method, Object[] args);
    }
}
