package com.cold.tutorial.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author hui.liao
 *         2016/1/21 19:30
 */
public class ZKCreate {

    public static void create(String path, byte[] data) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zoo = ZooKeeperConnection.getConnection("192.168.126.128");
        zoo.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zoo.close();
    }

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        String path = "/javaNode";
        create(path, "java".getBytes());

    }
}
