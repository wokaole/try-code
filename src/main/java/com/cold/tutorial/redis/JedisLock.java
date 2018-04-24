package com.cold.tutorial.redis;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.UUID;

/**
 * 在多集群的场景下使用会有问题
 * @author faker
 * @date 2017/5/3 15:59.
 */
public class JedisLock {

    //锁默认超时时间，防止无限时持有锁
    public static final int DEFAULT_EXPIRY_TIME_MILLIS = 60 * 1000;
    //锁等待时间
    public static final int DEFAULT_ACQUIRE_TIME_MILLIS = 10 * 1000;
    //获取不到锁延迟时间
    private static final int DEFAULT_ACQUIRE_RESOLUTION_MILLIS = 100;

    private final Jedis jedis;
    private final String lockKeyPath;
    private final int expiryTimeMillis;
    private final int acquireTimeMillis;
    private final UUID uuid;
    private Lock lock;

    public JedisLock(Jedis jedis, String lockKeyPath) {
       this(jedis, lockKeyPath, DEFAULT_EXPIRY_TIME_MILLIS);
    }

    public JedisLock(Jedis jedis, String lockKeyPath, int expiryTimeMillis) {
        this(jedis, lockKeyPath, expiryTimeMillis, DEFAULT_ACQUIRE_TIME_MILLIS);
    }

    public JedisLock(Jedis jedis, String lockKeyPath, int expiryTimeMillis, int acquireTimeMillis) {
        this(jedis, lockKeyPath, expiryTimeMillis, acquireTimeMillis, UUID.randomUUID());
    }

    public JedisLock(Jedis jedis, String lockKeyPath, int expiryTimeMillis, int acquireTimeMillis, UUID uuid) {
        this.jedis = jedis;
        this.lockKeyPath = lockKeyPath;
        this.expiryTimeMillis = expiryTimeMillis;
        this.acquireTimeMillis = acquireTimeMillis;
        this.uuid = uuid;
    }

    public synchronized boolean acquire() throws InterruptedException {

        int timeout = expiryTimeMillis;
        while (timeout >= 0) {
            Lock newLock = this.getLock(expiryTimeMillis + System.currentTimeMillis());
            //如果setnx成功说明key不存在,获取锁成功
            if (jedis.setnx(lockKeyPath, newLock.toString()) == 1) {
                this.lock = newLock;
                return true;
            }

            final String currentVal = jedis.get(lockKeyPath);
            Lock currentLock = Lock.valueOf(currentVal);
            //判断锁是否已经过期了
            if (currentLock.isExpireOrMine(this.getUuid())) {

                /*
                todo 如果集群部署，到这边会有问题，假设不同JVM中的两个线程都到这步了， 1线程执行完获取到锁后，2线程也执行也会获取到锁，
                相当于多个线程获取到锁了，如果线程1然后释放锁就会把把线程2的锁释放了
                 */
                String oldVal = jedis.getSet(lockKeyPath, newLock.toString());
                if (StringUtils.isNotBlank(oldVal) && StringUtils.equals(oldVal, currentVal)) {
                    this.lock = newLock;
                    return true;
                }
            }

            timeout -= DEFAULT_ACQUIRE_RESOLUTION_MILLIS;
            Thread.sleep(DEFAULT_ACQUIRE_RESOLUTION_MILLIS);

        }

        return false;
    }

    public synchronized void release() {
        if (this.isLocked()) {
            jedis.del(this.lockKeyPath);
            this.lock = null;
        }
    }

    private Lock getLock(long expireMills) {
        return new Lock(uuid, expireMills);
    }

    private boolean isLocked() {
        return this.lock != null;
    }

    public UUID getUuid() {
        return uuid;
    }

    public static class Lock {
        private UUID uuid;
        private long expireMillis;

        public Lock(UUID uuid, long expireMillis) {
            this.uuid = uuid;
            this.expireMillis = expireMillis;
        }

        @Override
        public String toString() {
            return uuid.toString() + ":" + expireMillis;
        }

        public static Lock valueOf(String str) {
            String[] values = StringUtils.split(str, ":");
            UUID uuid = UUID.fromString(values[0]);
            long expireMillis = Long.parseLong(values[1]);
            return new Lock(uuid, expireMillis);
        }

        public boolean isExpire() {
            return this.getExpireMillis() < System.currentTimeMillis();
        }

        public boolean isExpireOrMine(UUID uuid) {
            return this.isExpire() || this.getUuid().equals(uuid);
        }

        public UUID getUuid() {
            return uuid;
        }

        public long getExpireMillis() {
            return expireMillis;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JedisLock lock = new JedisLock(new Jedis(), "lock");
        try {
            if (lock.acquire()) {
                //todo
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起
            lock.release();
        }
    }
}
