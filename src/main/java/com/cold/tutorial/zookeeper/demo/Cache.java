package com.cold.tutorial.zookeeper.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

/**
 * @author liaowenhui
 * @date 2016/8/24 10:37.
 */
public class Cache {

    public static PathChildrenCache pathChildrenCache(CuratorFramework client, String path, Boolean cacheData) throws Exception {

        PathChildrenCache cache = new PathChildrenCache(client, path, cacheData);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                PathChildrenCacheEvent.Type type = event.getType();

                switch (type) {
                    case CONNECTION_RECONNECTED:
                        cache.rebuild();
                        break;
                    case CONNECTION_SUSPENDED:
                        break;
                    case CONNECTION_LOST:
                        System.out.println("connection error...");
                    default:
                        System.out.println("PathChildrenCache changed : {path:" + event.getData().getPath() + " data:" +
                                new String(event.getData().getData()) + "}");
                }
            }
        });

        return cache;
    }

    public static void main(String[] args) throws Exception {

        CuratorFramework build = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .connectionTimeoutMs(3).sessionTimeoutMs(4).build();
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
        client.getCuratorListenable().addListener(new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework curatorFramework, CuratorEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILDREN:
                        break;
                    case CREATE:
                }
            }
        });
        client.start();

        client.checkExists().inBackground().forPath("/create");

        LeaderLatch latch = new LeaderLatch(client, "test");
        latch.addListener(new LeaderLatchListener() {
            @Override
            public void isLeader() {

            }

            @Override
            public void notLeader() {

            }
        });

        PathChildrenCache cache = pathChildrenCache(client, "/create", true);
        cache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        List<ChildData> datas = cache.getCurrentData();
        for (ChildData data : datas) {
            System.out.println("pathcache:{" + data.getPath() + ":" + new String(data.getData())+"}");
        }

    }
}
