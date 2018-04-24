package com.cold.tutorial.log;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liaowenhui
 * @date 2016/7/22 17:31.
 */
public class LogUtil {

    /**
     * log 名称必须与appender中的对应
     */
    private static final Logger log = LoggerFactory.getLogger("rocketMQLog");

    public static void error(MessageInfo message) {
        String msg = JSON.toJSONString(message);
        log.error(msg);
    }

    public static void warn(MessageInfo message) {
        String msg = JSON.toJSONString(message);
        log.warn(msg);
    }

    public static void info(MessageInfo message) {
        String msg = JSON.toJSONString(message);
        log.info(msg);
    }

}
