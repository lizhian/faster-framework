package faster.framework.core.cache.interfaces;

import faster.framework.core.cache.container.CacheContainer;
import faster.framework.core.exception.biz.FrameworkException;

import java.time.Duration;
import java.util.function.Function;

/**
 * 缓存接口标记
 */
public interface CacheInterface<T> {


    /**
     * 请在子类或子接口重写此方法
     * <p>
     * 未命中缓存的时候获取实时缓存值
     *
     * @return {@link Function}<{@link String}, {@link T}>
     */
    default Function<String, T> valueFunction() {
        throw FrameworkException.of("请在子类或子接口重写此方法");
    }

    default boolean has(String param) {
        return this.asCacheContainer().has(param);
    }


    default long size() {
        return this.asCacheContainer().size();
    }

    /**
     * 获取缓存值
     *
     * @param param 参数
     * @return {@link T}
     */
    default T get(String param) {
        return this.asCacheContainer().get(param);

    }

    /**
     * 更新缓存值
     *
     * @param param 参数
     * @param value 值
     */
    default void update(String param, T value) {
        this.asCacheContainer().update(param, value);
    }

    /**
     * 更新缓存值
     *
     * @param param      参数
     * @param value      值
     * @param timeToLive 活着时间
     */
    default void update(String param, T value, Duration timeToLive) {
        this.asCacheContainer().update(param, value, timeToLive);

    }

    /**
     * 清除缓存
     *
     * @param param 参数
     */
    default void clean(String param) {
        this.asCacheContainer().clean(param);

    }

    /**
     * 清除全部
     */
    default void cleanAll() {
        this.asCacheContainer().cleanAll();
    }

    default CacheContainer<T> asCacheContainer() {
        // FasterCache fasterCache = FasterCacheUtil.findFasterCache(this);
        // return FasterCacheUtil.buildCacheContainer(fasterCache, this);
        return null;
    }
}
