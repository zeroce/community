package life.maijiang.community.service.impl;

import life.maijiang.community.service.RedisService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("redisService")
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 默认过期时长，单位：秒
     */
    public static final long DEFAULT_eXPIRE = 60 * 60 * 24;

    /**
     * 不设置过期时长
     */
    public static final long NOT_EXPIRE = -1;

    @Override
    public void setKey(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String getKey(String key) {
        Object result2 = redisTemplate.opsForValue().get(key);
        String result = (String) redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = redisConnection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

    @Override
    public void expireKey(String key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    @Override
    public void expireKeyAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    @Override
    public long getKeyExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    @Override
    public void persistKey(String key) {
        redisTemplate.persist(key);
    }

    @Override
    public void removeKey(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void removeKey(String... keys) {
        Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    @Override
    public void removeKey(Collection<String> keys) {
        Set<String> kSet = keys.stream().map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    @Override
    public boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    @Override
    public boolean renameKeyNotExist(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }
}
