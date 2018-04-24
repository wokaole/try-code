package redis;

import org.junit.Test;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hui.liao on 2015/11/24.
 */
public class ShardedRedisTest {

    @Test
    public void testRedis() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(2);
        poolConfig.setMaxIdle(1);
        poolConfig.setMaxWaitMillis(2000);
        poolConfig.setTestOnBorrow(false);
        poolConfig.setTestOnReturn(false);

        String host = "127.0.0.1";
        JedisShardInfo shardInfo1 = new JedisShardInfo(host, 6379, 500);
        shardInfo1.setPassword("test123");
        JedisShardInfo shardInfo2 = new JedisShardInfo(host, 6380, 500);
        shardInfo2.setPassword("test123");
        JedisShardInfo shardInfo3 = new JedisShardInfo(host, 6381, 500);
        shardInfo3.setPassword("test123");

        //初始化ShardedJedisPool
        List<JedisShardInfo> infoList = Arrays.asList(shardInfo1, shardInfo2, shardInfo3);
        ShardedJedisPool jedisPool = new ShardedJedisPool(poolConfig, infoList);
        ShardedJedis jedis = jedisPool.getResource();
        ShardedJedis jedis1 = jedisPool.getResource();
        ShardedJedis jedis2 = jedisPool.getResource();
    }
}
