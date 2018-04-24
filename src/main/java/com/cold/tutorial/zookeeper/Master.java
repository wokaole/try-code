package com.cold.tutorial.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * zookeeper 实现 master-workers的同步版本
 * @author liaowenhui
 * @date 2016/8/23 13:53.
 */
public class Master implements Watcher, Runnable{

    private ZooKeeper zooKeeper;
    private final static String MASTER_PATH = "/master";
    private String connectString;
    private String serverId;

    public Master(String connectString, String serverId) {
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
        try {
            zooKeeper.create(MASTER_PATH, serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean checkForMaster() {
        try {
            byte[] data = zooKeeper.getData(MASTER_PATH, this, new Stat());
            return serverId.equals(new String(data));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registerForMaster() {
        boolean isLeader = false;
        while (true) {
            if (checkForMaster()) {
                isLeader = true;
                System.out.println("you are register master");
                break;
            } else {
                try {
                    createMasterNode();
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return isLeader;
    }

    @Override
    public void run() {
        startZK();
        boolean b = registerForMaster();
        if (b) {
            stopZK();
        }

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    public static void main(String[] args) {
        int num = 5;
        ExecutorService pool = Executors.newFixedThreadPool(num);
        for (int i=0; i<num; i++) {
            Master master = new Master("localhost:2181", "ma:" + i);
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
