package com.cold.tutorial.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.KeeperException.*;
import org.apache.zookeeper.data.Stat;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * master-workers的异步版本
 * @author liaowenhui
 * @date 2016/8/23 14:38.
 */
public class AsyncMaster implements Watcher, Runnable {
    private ZooKeeper zooKeeper;
    private final static String MASTER_PATH = "/master";
    private String connectString;
    private String serverId;

    public AsyncMaster(String connectString, String serverId) {
        this.connectString = connectString;
        this.serverId = serverId;
    }

    public void startZK() {
        try {
            zooKeeper = new ZooKeeper(connectString, 2000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopZK() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void createMasterNode() {
        zooKeeper.create(MASTER_PATH, serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                new AsyncCallback.StringCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx, String name) {
                        Code code = Code.get(rc);
                        switch (code) {
                            case OK:
                                System.out.println("create master ok");
                                stopZK();
                                break;
                            case NODEEXISTS:
                                System.out.println("node exists");
                                checkForMaster();
                                break;
                            case SESSIONEXPIRED:
                                System.out.println("session expired in create");
                                break;
                            default:
                                checkForMaster();
                                System.out.println("code is " + code);
                        }
                    }
                }, "ctx:" + serverId);
    }

    public void checkForMaster() {

        zooKeeper.getData(MASTER_PATH, true, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                Code code = Code.get(rc);
                switch (code) {
                    case OK:
                        if (new String(data).equals(serverId)) {
                            System.out.println("stop now");
                            stopZK();
                        } else {
                            checkForMaster();
                        }
                        break;
                    case NONODE:
                        System.out.println("node not exists");
                        createMasterNode();
                        break;
                    case NODEEXISTS:
                        System.out.println("node exists");
                        createMasterNode();
                        break;
                    case SESSIONEXPIRED:
                        System.out.println("session expired in check");
                        break;
                    default:
                        System.out.println("code is " + code);
                        checkForMaster();
                }
            }
        }, null);
    }

    public void registerForMaster() {
        checkForMaster();
    }

    @Override
    public void run() {
        startZK();
        registerForMaster();

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    public static void main(String[] args) {
        int num = 5;
        ExecutorService pool = Executors.newFixedThreadPool(num);
        for (int i=0; i<num; i++) {
            AsyncMaster master = new AsyncMaster("localhost:2181", "ma:" + i);
            pool.execute(master);
        }

        pool.shutdown();
        try {
            pool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
