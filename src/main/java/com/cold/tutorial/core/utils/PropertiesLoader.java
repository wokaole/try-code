package com.cold.tutorial.core.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * Properties 文件载入类，可以一次性载入多个文件，相同属性值会覆盖之前的，但以system.getProperty优先。
 * Created by faker on 2015/9/13.
 */
public class PropertiesLoader {

    private static final Logger log = LoggerFactory.getLogger(PropertiesLoader.class);
    private Properties properties;
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    public PropertiesLoader(String... paths) {
        properties = loadProperties(paths);
    }

    public String getValue(String key) {
        String property = System.getProperty(key);
        if (property != null) {
            return property;
        }
        return properties.getProperty(key);
    }

    public String getProperty(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return value;
    }

    public String getProperty(String key, String defaultValue) {
        String property = getProperty(key);
        return property != null ? property : defaultValue;
    }

    public Integer getInteger(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Integer.valueOf(value);
    }

    public Integer getInteger(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null ? Integer.valueOf(value) : defaultValue;
    }

    private Properties loadProperties(String[] paths) {
        Properties properties = new Properties();

        for(String path : paths) {
            log.debug("load resource from path : {}", path);

            InputStream is = null;
            try {
                Resource resource = resourceLoader.getResource(path);
                is = resource.getInputStream();
                properties.load(is);
            } catch (IOException e) {
                log.error("can't load resource from path: " + path, e);
            } finally {
                IOUtils.closeQuietly(is);
            }
        }
        return properties;
    }

}
