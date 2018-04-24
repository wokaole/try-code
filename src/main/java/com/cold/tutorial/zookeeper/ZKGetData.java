package com.cold.tutorial.zookeeper;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;


/**
 * @author hui.liao
 *         2016/1/21 19:52
 */
public class ZKGetData {
    public static void main(String[] args) throws Exception {
        ZooKeeper zoo = ZooKeeperConnection.getConnection("192.168.126.128");
        CountDownLatch latch = new CountDownLatch(1);

        byte[] data = zoo.getData("/javaNode", (event) -> {
            if (event.getType() == Watcher.Event.EventType.None) {
                switch(event.getState()) {
                    case Expired:
                        latch.countDown();
                        break;
                }

            } else {
                System.out.println("processing");
                latch.countDown();
            }
        }, null);

        System.out.println(new String(data));
        latch.await();
    }
}
