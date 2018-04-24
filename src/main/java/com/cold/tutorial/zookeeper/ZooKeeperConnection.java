package com.cold.tutorial.zookeeper;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author hui.liao
 *         2016/1/21 18:11
 */
public class ZooKeeperConnection {

    private static ZooKeeper zooKeeper;

    public static ZooKeeper getConnection(String host) throws IOException, InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        zooKeeper = new ZooKeeper(host, 5000, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                latch.countDown();
            }
            System.out.println("111 " + event.getState());
        });

        latch.await();
        return zooKeeper;
    }

    public static void close() throws InterruptedException {
        if (zooKeeper != null) {
            zooKeeper.close();
        }
    }

    public static void main(String[] args) throws Exception {
    }
}
