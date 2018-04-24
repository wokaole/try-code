package com.cold.tutorial.zookeeper.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.ZKPaths;

/**
 * @author liaowenhui
 * @date 2016/8/24 15:18.
 */
public class Main {

    public static void main(String[] args) {
        String nodeFromPath = ZKPaths.getNodeFromPath("/test/tt/te3/32/f");
        System.out.println(nodeFromPath);
        String path = ZKPaths.makePath("tets", "tt");
        System.out.println(path);

        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
        client.start();
        client.start();

        CloseableUtils.closeQuietly(client);

    }
}
