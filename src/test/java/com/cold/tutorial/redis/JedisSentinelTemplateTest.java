package com.cold.tutorial.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author liaowenhui
 * @date 2017/5/23 16:39.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-redis.xml")
public class JedisSentinelTemplateTest {
    @Autowired
    private JedisTemplate jedisSentinelTemplate;
    @Autowired
    private JedisTemplate jedisTemplate;

    @Test
    public void get() throws Exception {
        System.out.println(jedisSentinelTemplate.set("test", "test3"));
        System.out.println(jedisSentinelTemplate.get("test"));
        System.out.println(jedisTemplate.get("test"));
    }

}