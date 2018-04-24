package com.cold.tutorial.redis;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.UUID;

/**
 * @author faker
 * @date 2017/5/3 17:48.
 */
public class JedisShardedLock {
    //锁默认超时时间，防止无限时持有锁
    public static final int DEFAULT_EXPIRY_TIME_MILLIS = 60 * 1000;
    //锁等待时间
    public static final int DEFAULT_ACQUIRE_TIME_MILLIS = 10 * 1000;
    //获取不到锁延迟时间
    private static final int DEFAULT_ACQUIRE_RESOLUTION_MILLIS = 100;

    private volatile boolean locked = false;

    private final Jedis jedis;
    private final String lockKeyPath;
    private final int expiryTimeMillis;
    private final int acquireTimeMillis;

    private final UUID lockValue = UUID.randomUUID();

    public JedisShardedLock(Jedis jedis, String lockKeyPath) {
        this(jedis, lockKeyPath, DEFAULT_EXPIRY_TIME_MILLIS);
    }

    public JedisShardedLock(Jedis jedis, String lockKeyPath, int expiryTimeMillis) {
        this(jedis, lockKeyPath, expiryTimeMillis, DEFAULT_ACQUIRE_TIME_MILLIS);
    }

    public JedisShardedLock(Jedis jedis, String lockKeyPath, int expiryTimeMillis, int acquireTimeMillis) {
        this.jedis = jedis;
        this.lockKeyPath = lockKeyPath;
        this.expiryTimeMillis = expiryTimeMillis;
        this.acquireTimeMillis = acquireTimeMillis;
    }

    public synchronized boolean acquire() throws InterruptedException {
        int timeout = acquireTimeMillis;
        while (timeout >= 0) {
            String result = jedis.set(lockKeyPath, lockValue.toString(), "NX", "PX", expiryTimeMillis);
            if (StringUtils.equals("OK", result)) {
                locked = true;
                return true;
            }

            timeout -= DEFAULT_ACQUIRE_RESOLUTION_MILLIS;
            Thread.sleep(DEFAULT_ACQUIRE_RESOLUTION_MILLIS);

        }
        return false;
    }

    public synchronized void release() {
        if (locked && StringUtils.equals(jedis.get(lockKeyPath), lockValue.toString())) {
            jedis.del(lockKeyPath);
            locked = false;
        }
    }
}
