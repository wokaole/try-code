package com.cold.tutorial.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.util.Pool;

/**
 * @author liaowenhui
 * @date 2017/5/23 14:56.
 */
public class JedisSentinelTemplate {

    private Pool<Jedis> jedisPool;
    private static Logger log = LoggerFactory.getLogger(JedisSentinelTemplate.class);


    public <T> T execute(JedisAction<T> jedisAction) {
        try(Jedis jedis = jedisPool.getResource()) {
            T t = jedisAction.action(jedis);
            return t;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public String set(final String key, final String value) {
        return execute(jedis -> jedis.set(key, value));
    }

    public String get(final String key) {
        return execute(jedis -> jedis.get(key));
    }

    public interface JedisAction<T> {
        T action(Jedis jedis);
    }

    public interface JedisActionNoResult {
        void action(Jedis jedis);
    }

    public interface PipelineAction {
        void action(Pipeline pipeline);
    }

    public interface PipelineActionNoResult {
        void action(Pipeline pipeline);
    }

    public void setJedisPool(Pool<Jedis> jedisPool) {
        this.jedisPool = jedisPool;
    }
}
