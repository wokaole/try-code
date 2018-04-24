package com.cold.tutorial.redis;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.util.Pool;

import java.util.*;

/**
 * Created by hui.liao on 2015/11/20.
 */
public class JedisTemplate {

    private static Logger log = LoggerFactory.getLogger(JedisTemplate.class);
    private Pool<Jedis> jedisPool;

    public <T> T execute(JedisAction<T> jedisAction) {
        try(Jedis jedis = jedisPool.getResource()) {

            T t = jedisAction.action(jedis);
            return t;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public void execute(JedisActionNoResult jedisAction) {
        try(Jedis jedis = jedisPool.getResource()) {

            jedisAction.action(jedis);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public List<Object> execute(PipelineAction pipelineAction) {
        try(Jedis jedis = jedisPool.getResource()) {

            Pipeline pipeline = jedis.pipelined();
            pipelineAction.action(pipeline);
            List<Object> resultList = pipeline.syncAndReturnAll();
            return resultList;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public void execute(PipelineActionNoResult pipelineAction) {
        try(Jedis jedis = jedisPool.getResource()) {

            Pipeline pipeline = jedis.pipelined();
            pipelineAction.action(pipeline);
            pipeline.sync();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    //Common Action
    public List<String> scan(final String pattern) {
        return execute(new JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                ScanParams params = new ScanParams();
                params.match(pattern).count(200);

                Set<String> sets = Sets.newHashSet();
                String stringCursor = ScanParams.SCAN_POINTER_START;
                do {
                    ScanResult<String> result = jedis.scan(stringCursor, params);
                    List<String> resultList = result.getResult();
                    if (resultList != null && !resultList.isEmpty()) {
                        sets.addAll(resultList);
                    }
                    stringCursor = result.getStringCursor();
                } while (!ScanParams.SCAN_POINTER_START.equals(stringCursor));

                if (sets == null || sets.isEmpty()) {
                    return Collections.emptyList();
                }
                return new ArrayList<>(sets);
            }
        });
    }

    public boolean del(final String... keys) {
        return execute(new JedisAction<Boolean>() {
            @Override
            public Boolean action(Jedis jedis) {
                return jedis.del(keys) == keys.length ? true : false;
            }
        });
    }

    public void watch(Jedis jedis, String... keys) {
        jedis.watch(keys);
    }

    public void unwatch(Jedis jedis) {
        jedis.unwatch();
    }

    public void delByPipeline(final String... keys) {
        execute(new PipelineActionNoResult() {
            @Override
            public void action(Pipeline pipeline) {
                pipeline.del(keys);
            }
        });
    }

    // String Action
    public String get(final String key) {
        return execute(new JedisAction<String>() {
            @Override
            public String action(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    public Long getAsLong(final String key) {
        String result = get(key);
        return result != null ? Long.valueOf(result) : null;
    }

    public Integer getAsInt(final String key) {
        String result = get(key);
        return result != null ? Integer.valueOf(result) : null;
    }

    public List<String> mget(final String... keys) {
        return execute(new JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                return jedis.mget(keys);
            }
        });
    }

    public String set(final String key, final String value) {
        return execute(new JedisAction<String>() {

            @Override
            public String action(Jedis jedis) {
                return jedis.set(key, value);
            }
        });
    }

    public String setex(final String key, final String value, final int seconds) {
        return execute(new JedisAction<String>() {

            @Override
            public String action(Jedis jedis) {
                return jedis.setex(key, seconds, value);
            }
        });
    }

    public void setByPipeline(final Map<String, String> map) throws Exception {
        if (map == null || map.isEmpty()) {
            throw new Exception("map data is null!");
        }

        execute(new PipelineActionNoResult() {
            @Override
            public void action(Pipeline pipeline) {
                Set<Map.Entry<String, String>> set = map.entrySet();
                for (Map.Entry<String, String> entry : set) {
                    pipeline.set(entry.getKey(), entry.getValue());
                }
            }
        });
    }

    // List Action
    public Long lpush(final String key, final String... values) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.lpush(key, values);
            }
        });
    }

    public void lpushByPipeline(final Map<String, String> map) throws Exception {
        if (map == null || map.isEmpty()) {
            throw new Exception("map data is null!");
        }

        execute(new PipelineActionNoResult() {
            @Override
            public void action(Pipeline pipeline) {
                Set<Map.Entry<String, String>> set = map.entrySet();
                for (Map.Entry<String, String> entry : set) {
                    pipeline.lpush(entry.getKey(), entry.getValue());
                }
            }
        });
    }
    public String rpop(final String key) {
        return execute(new JedisAction<String>() {
            @Override
            public String action(Jedis jedis) {
                return jedis.rpop(key);
            }
        });
    }

    public List<String> brpop(final String key, final int timeout) {
        return execute(new JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                return jedis.brpop(timeout, key);
            }
        });
    }

    public List<String> lrange (final String key, final int start, final int stop) {
        return execute(new JedisAction<List<String>>() {
            @Override
            public List<String> action(Jedis jedis) {
                return jedis.lrange(key, start, stop);
            }
        });
    }

    // Hash Action

    public Long hincyby(final String key, final String field, final long value) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.hincrBy(key, field, value);
            }
        });
    }

    public void hincyByPipeline(final String key, final Map<String, Long> map) {
        execute(new PipelineActionNoResult() {
            @Override
            public void action(Pipeline pipeline) {
                Set<Map.Entry<String, Long>> set = map.entrySet();
                for (Map.Entry<String, Long> entry : set) {
                    pipeline.hincrBy(key, entry.getKey(), entry.getValue());
                }
            }
        });
    }

    public String hget(final String key, final String field) {
        return execute(new JedisAction<String>() {
            @Override
            public String action(Jedis jedis) {
                return jedis.hget(key, field);
            }
        });
    }

    public long hset(final String key, final String field, final String value) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.hset(key, field, value);
            }
        });
    }

    public Map<String, String> hgetAll(final String key) {
        return execute(new JedisAction<Map<String, String>>() {
            @Override
            public Map<String, String> action(Jedis jedis) {
                return jedis.hgetAll(key);
            }
        });
    }


    public void hmsetByPipeline(final String key, final Map<String, String> map) throws RuntimeException {
        if (map == null || map.isEmpty()) {
            throw new RuntimeException("map data is null!");
        }

        execute(new PipelineActionNoResult() {
            @Override
            public void action(Pipeline pipeline) {
                pipeline.hmset(key, map);
            }
        });
    }

    //SortedSet Action

    public long zadd(final String key, final double score, final String member) {
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.zadd(key, score, member);
            }
        });
    }

    public void zaddByPipeline(final String key, final Map<String, Double> scoreMembers) {
        execute(new PipelineActionNoResult() {
            @Override
            public void action(Pipeline pipeline) {
                pipeline.zadd(key, scoreMembers);
            }
        });
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
