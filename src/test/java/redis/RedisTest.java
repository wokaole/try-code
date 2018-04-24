package redis;

import com.cold.tutorial.redis.JedisTemplate;
import com.cold.tutorial.redis.ShardedJedisTemplate;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * Created by hui.liao on 2015/11/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-redis.xml"})
public class RedisTest {

    @Autowired
    private JedisTemplate jedisTemplate;
    @Autowired
    private ShardedJedisTemplate shardedJedisTemplate;
    @Test
    public void testGet() {
        String test = jedisTemplate.get("CGLibProxy");
        if (StringUtils.isBlank(test)) {
            jedisTemplate.set("CGLibProxy", "CGLibProxy");
        }
        System.out.println(jedisTemplate.get("CGLibProxy"));
    }

    @Test
    public void testShardedGet() throws InterruptedException {
//        shardedJedisTemplate.set("atest5", "test5");
//        shardedJedisTemplate.set("atest1", "test1");
//        shardedJedisTemplate.set("atest2", "test2");
//        shardedJedisTemplate.set("atest3", "test3");
//        shardedJedisTemplate.set("atest4", "test4");
        System.out.println(shardedJedisTemplate.get("atest1"));
        System.out.println(shardedJedisTemplate.get("atest2"));
        System.out.println(shardedJedisTemplate.get("atest3"));
        System.out.println(shardedJedisTemplate.get("atest4"));
        System.out.println(shardedJedisTemplate.get("atest5"));
    }
}
