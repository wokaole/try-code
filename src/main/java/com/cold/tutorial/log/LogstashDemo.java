package com.cold.tutorial.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

/**
 * Created by faker on 2016/7/17.
 */
public class LogstashDemo {

    public static final Logger log = LoggerFactory.getLogger(LogstashDemo.class);

    public static void main(String[] args) {
        IntStream.range(0, 10).forEach(i -> {
            log.error("error [{}]", i);
        });
    }
}
