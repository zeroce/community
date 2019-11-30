package life.maijiang.community.service;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public interface RedisService {
    /**
     * set 缓存数据
     * @param key
     * @param value
     */
    void setKey(String key, String value);

    /**
     * get 查找数据
     * @param key
     * @return
     */
    String getKey(String key);

    /**
     * 设置key有效时间
     * @param key
     * @param time
     * @param timeUnit
     * @return
     */
    void expireKey(String key, long time, TimeUnit timeUnit);

    /**
     * 指定 key 在指定的日期过期
     * @param key
     * @param date
     */
    void expireKeyAt(String key, Date date);

    /**
     * 查询 key 的生命周期
     * @param key
     * @param timeUnit
     * @return
     */
    long getKeyExpire(String key, TimeUnit timeUnit);

    /**
     * 将 key 设置为永久有效
     * @param key
     */
    void persistKey(String key);

    /**
     * remove 移除 key
     * @param key
     * @return
     */
    void removeKey(String key);

    /**
     * 删除多个 key
     * @param keys
     * @return
     */
    void removeKey(String ... keys);

    /**
     * 删除 key 集合
     * @param keys
     * @return
     */
    void removeKey(Collection<String> keys);

    /**
     *
     * @param key
     * @return
     */
    boolean existsKey(String key);

    /**
     *
     * @param oldKey
     * @param newKey
     */
    void renameKey(String oldKey, String newKey);

    /**
     *
     * @param oldKey
     * @param newKey
     * @return
     */
    boolean renameKeyNotExist(String oldKey, String newKey);


}
