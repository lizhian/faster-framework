package faster.framework.core.util;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.toolkit.support.*;
import faster.framework.core.function.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 获取Lambda方法的信息
 *
 * @see com.baomidou.mybatisplus.core.toolkit.LambdaUtils
 */
@Slf4j
public class LambdaUtil {

    public static <T> Method getMethod(SFunction<T, ?> func) {
        return LambdaUtil.getMethod((Serializable) func);
    }

    public static <T> String getMethodName(SFunction<T, ?> func) {
        return LambdaUtil.getMethodName((Serializable) func);
    }

    public static <T> Field getField(SFunction<T, ?> func) {
        return LambdaUtil.getField((Serializable) func);
    }

    public static <T> String getFieldName(SFunction<T, ?> func) {
        return LambdaUtil.getFieldName((Serializable) func);
    }

    //-----SConsumer
    public static <T> Class<T> getEntityClass(SConsumer<T> func) {
        return LambdaUtil.getEntityClass((Serializable) func);
    }

    public static <T> Method getMethod(SConsumer<T> func) {
        return LambdaUtil.getMethod((Serializable) func);
    }

    public static <T> String getMethodName(SConsumer<T> func) {
        return LambdaUtil.getMethodName((Serializable) func);
    }

    public static <T> Field getField(SConsumer<T> func) {
        return LambdaUtil.getField((Serializable) func);
    }

    public static <T> String getFieldName(SConsumer<T> func) {
        return LambdaUtil.getFieldName((Serializable) func);
    }

    //-----SPredicate
    public static <T> Class<T> getEntityClass(SPredicate<T> func) {
        return LambdaUtil.getEntityClass((Serializable) func);
    }

    public static <T> Method getMethod(SPredicate<T> func) {
        return LambdaUtil.getMethod((Serializable) func);
    }

    public static <T> String getMethodName(SPredicate<T> func) {
        return LambdaUtil.getMethodName((Serializable) func);
    }

    public static <T> Field getField(SPredicate<T> func) {
        return LambdaUtil.getField((Serializable) func);
    }

    public static <T> String getFieldName(SPredicate<T> func) {
        return LambdaUtil.getFieldName((Serializable) func);
    }

    //-----SSupplier
    public static <T> Class<T> getEntityClass(SSupplier<T> func) {
        return LambdaUtil.getEntityClass((Serializable) func);
    }

    public static <T> Method getMethod(SSupplier<T> func) {
        return LambdaUtil.getMethod((Serializable) func);
    }

    public static <T> String getMethodName(SSupplier<T> func) {
        return LambdaUtil.getMethodName((Serializable) func);
    }

    public static <T> Field getField(SSupplier<T> func) {
        return LambdaUtil.getField((Serializable) func);
    }

    public static <T> String getFieldName(SSupplier<T> func) {
        return LambdaUtil.getFieldName((Serializable) func);
    }

    //-----SFunction
    public static <T> Class<T> getEntityClass(BiSConsumer<T, ?> func) {
        return LambdaUtil.getEntityClass((Serializable) func);
    }

    public static <T> Method getMethod(BiSConsumer<T, ?> func) {
        return LambdaUtil.getMethod((Serializable) func);
    }

    public static <T> String getMethodName(BiSConsumer<T, ?> func) {
        return LambdaUtil.getMethodName((Serializable) func);
    }

    public static <T> Field getField(BiSConsumer<T, ?> func) {
        return LambdaUtil.getField((Serializable) func);
    }

    public static <T> String getFieldName(BiSConsumer<T, ?> func) {
        return LambdaUtil.getFieldName((Serializable) func);
    }

    //-----BiSFunction
    public static <T> Class<T> getEntityClass(BiSFunction<T, ?, ?> func) {
        return LambdaUtil.getEntityClass((Serializable) func);
    }

    public static <T> Method getMethod(BiSFunction<T, ?, ?> func) {
        return LambdaUtil.getMethod((Serializable) func);
    }

    public static <T> String getMethodName(BiSFunction<T, ?, ?> func) {
        return LambdaUtil.getMethodName((Serializable) func);
    }

    public static <T> Field getField(BiSFunction<T, ?, ?> func) {
        return LambdaUtil.getField((Serializable) func);
    }

    public static <T> String getFieldName(BiSFunction<T, ?, ?> func) {
        return LambdaUtil.getFieldName((Serializable) func);
    }

    //-----SFunction
    public static <T> Class<T> getEntityClass(BiSPredicate<T, ?> func) {
        return LambdaUtil.getEntityClass((Serializable) func);
    }

    public static <T> Method getMethod(BiSPredicate<T, ?> func) {
        return LambdaUtil.getMethod((Serializable) func);
    }

    public static <T> String getMethodName(BiSPredicate<T, ?> func) {
        return LambdaUtil.getMethodName((Serializable) func);
    }

    public static <T> Field getField(BiSPredicate<T, ?> func) {
        return LambdaUtil.getField((Serializable) func);
    }

    public static <T> String getFieldName(BiSPredicate<T, ?> func) {
        return LambdaUtil.getFieldName((Serializable) func);
    }


    //-----实现方法
    @SuppressWarnings("unchecked")
    private static <T> Class<T> getEntityClass(Serializable serializableFunc) {
        return (Class<T>) LambdaUtil.extract(serializableFunc).getInstantiatedClass();
    }

    private static Method getMethod(Serializable serializableFunc) {
        LambdaMeta extract = LambdaUtil.extract(serializableFunc);
        Class<?> clazz = extract.getInstantiatedClass();
        String methodName = extract.getImplMethodName();
        return ReflectUtil.getMethodByName(clazz, methodName);
    }

    private static String getMethodName(Serializable serializableFunc) {
        LambdaMeta extract = LambdaUtil.extract(serializableFunc);
        return extract.getImplMethodName();
    }

    /**
     * 根据Lambda方法获取Field
     */
    private static Field getField(Serializable serializableFunc) {
        LambdaMeta extract = LambdaUtil.extract(serializableFunc);
        Class<?> clazz = extract.getInstantiatedClass();
        String methodName = extract.getImplMethodName();
        String name = PropertyNamer.methodToProperty(methodName);
        return ReflectUtil.getField(clazz, name);
    }

    private static String getFieldName(Serializable serializableFunc) {
        Field field = LambdaUtil.getField(serializableFunc);
        if (field == null) {
            return null;
        }
        return field.getName();
    }

    /**
     * 获取Lambda方法元数据
     */
    private static LambdaMeta extract(Serializable func) {
        if (func instanceof Proxy) {
            return new IdeaProxyLambdaMeta((Proxy) func);
        }
        // 2. 反射读取
        try {
            Method method = func.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            return new ReflectLambdaMeta((java.lang.invoke.SerializedLambda) method.invoke(func), func.getClass().getClassLoader());
        } catch (Throwable e) {
            // 3. 反射失败使用序列化的方式读取
            return new ShadowLambdaMeta(SerializedLambda.extract(func));
        }
    }
}
