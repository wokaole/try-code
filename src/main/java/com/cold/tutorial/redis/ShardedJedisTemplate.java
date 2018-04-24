package com.cold.tutorial.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hui.liao on 2015/11/21.
 */
public class ShardedJedisTemplate {
    private static Logger log = LoggerFactory.getLogger(ShardedJedisTemplate.class);
    private ShardedJedisPool shardedJedisPool;

    public <T> T execute(ShardedJedisAction<T> jedisAction) {
        try(ShardedJedis jedis = shardedJedisPool.getResource()) {

            T t = jedisAction.action(jedis);
            return t;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public void execute(ShardedJedisActionNoResult jedisAction) {
        try(ShardedJedis jedis = shardedJedisPool.getResource()) {

            jedisAction.action(jedis);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public List<Object> execute(ShardedPipelineAction jedisAction) {
        try(ShardedJedis jedis = shardedJedisPool.getResource()) {

            ShardedJedisPipeline pipelined = jedis.pipelined();
            jedisAction.action(pipelined);
            List<Object> resultList = pipelined.syncAndReturnAll();
            return resultList;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public void execute(ShardedPipelineActionNoResult jedisAction) {
        try(ShardedJedis jedis = shardedJedisPool.getResource()) {

            ShardedJedisPipeline pipelined = jedis.pipelined();
            jedisAction.action(pipelined);
            pipelined.sync();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    // Common Action
    public void del(final String key) {
        execute(new ShardedJedisActionNoResult() {
            @Override
            public void action(ShardedJedis jedis) {
                jedis.del(key);
            }
        });
    }

    // String Action
    public String set(final String key, final String value) {
        return execute(new ShardedJedisAction<String>() {
            @Override
            public String action(ShardedJedis jedis) {
                return jedis.set(key, value);
            }
        });
    }

    public String get(final String key) {
        return execute(new ShardedJedisAction<String>() {
            @Override
            public String action(ShardedJedis jedis) {
                return jedis.get(key);
            }
        });
    }

    // List Action
    public Long lpush(final String key, final String... values) {
        return execute(new ShardedJedisAction<Long>() {
            @Override
            public Long action(ShardedJedis jedis) {
                return jedis.lpush(key, values);
            }
        });
    }

    public void lpushByPipeline(final Map<String, String> map) throws Exception {
        execute(new ShardedPipelineActionNoResult() {
            @Override
            public void action(ShardedJedisPipeline pipeline) {

                Set<Map.Entry<String, String>> set = map.entrySet();
                for (Map.Entry<String, String> entry : set) {
                    pipeline.lpush(entry.getKey(), entry.getValue());
                }
            }
        });
    }

    public String rpop(final String key) {
        return execute(new ShardedJedisAction<String>() {
            @Override
            public String action(ShardedJedis jedis) {
                return jedis.rpop(key);
            }
        });
    }

    public List<String> brpop(final String key, final int timeout) {
        return execute(new ShardedJedisAction<List<String>>() {
            @Override
            public List<String> action(ShardedJedis jedis) {
                return jedis.brpop(timeout, key);
            }
        });
    }

    public List<String> lrange (final String key, final int start, final int stop) {
        return execute(new ShardedJedisAction<List<String>>() {
            @Override
            public List<String> action(ShardedJedis jedis) {
                return jedis.lrange(key, start, stop);
            }
        });
    }

    // Hash Action
    public String hget(final String key, final String field) {
        return execute(new ShardedJedisAction<String>() {
            @Override
            public String action(ShardedJedis jedis) {
                return jedis.hget(key, field);
            }
        });
    }

    public Map<String, String> hgetAll(final String key) {
        return execute(new ShardedJedisAction<Map<String, String>>() {
            @Override
            public Map<String, String> action(ShardedJedis jedis) {
                return jedis.hgetAll(key);
            }
        });
    }

    public Long hset(final String key, final String field, final String value) {
        return execute(new ShardedJedisAction<Long>() {
            @Override
            public Long action(ShardedJedis jedis) {
                return jedis.hset(key, field, value);
            }
        });
    }

    public void hmsetByPipeline(final String key, final Map<String, String> map) throws RuntimeException {
        if (map == null || map.isEmpty()) {
            throw new RuntimeException("map data is null!");
        }

        execute(new ShardedPipelineActionNoResult() {
            @Override
            public void action(ShardedJedisPipeline pipeline) {
                pipeline.hmset(key, map);
            }
        });
    }


    //SortedSet Action
    public long zadd(final String key, final double score, final String member) {
        return execute(new ShardedJedisAction<Long>() {
            @Override
            public Long action(ShardedJedis jedis) {
                return jedis.zadd(key, score, member);
            }
        });
    }

    public long zcard(final String key) {
        return execute(new ShardedJedisAction<Long>() {
            @Override
            public Long action(ShardedJedis jedis) {
                return jedis.zcard(key);
            }
        });
    }

    public interface ShardedJedisAction<T> {
        T action(ShardedJedis jedis);
    }

    public interface ShardedJedisActionNoResult {
        void action(ShardedJedis jedis);
    }

    public interface ShardedPipelineAction {
        void action(ShardedJedisPipeline pipeline);
    }

    public interface ShardedPipelineActionNoResult {
        void action(ShardedJedisPipeline pipeline);
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

}
