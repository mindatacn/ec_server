//package com.mindata.ecserver.global.shiro;
//
//import org.apache.shiro.cache.Cache;
//import org.apache.shiro.cache.CacheException;
//import org.springframework.data.redis.core.StringRedisTemplate;
//
//import java.util.Collection;
//import java.util.Set;
//
///**
// * @author wuweifeng wrote on 2017/10/27.
// * shiro的redis缓存工具类
// */
//public class RedisCache implements Cache<Object, Object> {
//    private StringRedisTemplate redisTemplate;
//
//    private String key;
//
//    RedisCache(String key, StringRedisTemplate redisTemplate) {
//        this.key = key;
//        this.redisTemplate = redisTemplate;
//    }
//
//    @Override
//    public String get(Object s) throws CacheException {
//        Object object = redisTemplate.opsForHash().get(key, s);
//        if (object == null) {
//            return null;
//        }
//        return object.toString();
//    }
//
//    @Override
//    public Object put(Object s, Object o) throws CacheException {
//        redisTemplate.opsForHash().put(key, s, o);
//        return o;
//    }
//
//
//    @Override
//    public String remove(Object s) throws CacheException {
//        return redisTemplate.opsForHash().delete(key, s) + "";
//    }
//
//    @Override
//    public void clear() throws CacheException {
//        redisTemplate.opsForHash().delete(key);
//    }
//
//    @Override
//    public int size() {
//        return redisTemplate.opsForHash().size(key).intValue();
//    }
//
//    @Override
//    public Set<Object> keys() {
//        return null;
//    }
//
//    @Override
//    public Collection<Object> values() {
//        return null;
//    }
//}
