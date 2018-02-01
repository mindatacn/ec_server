package com.mindata.ecserver.global.cache.util;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author wuweifeng wrote on 2018/1/19.
 */
public class RedisBatchUtil {
    public static void pipelined(RedisTemplate redisTemplate, Consumer<Object> consumer) {
        redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                consumer.accept(null);
                return null;
            }
        });
    }

    public static void multi(RedisTemplate redisTemplate, Consumer<RedisOperations> consumer) {
        redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                consumer.accept(operations);

                // This will contain the results of all ops in the transaction
                return operations.exec();
            }
        });
    }

}
