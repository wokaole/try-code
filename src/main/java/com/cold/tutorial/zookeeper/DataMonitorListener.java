package com.cold.tutorial.zookeeper;

/**
 * @author hui.liao
 *         2016/1/22 10:54
 */
public interface DataMonitorListener {

    void exists(byte data[]);

    void closing(int rc);
}
