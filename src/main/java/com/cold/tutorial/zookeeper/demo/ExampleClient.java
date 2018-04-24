package com.cold.tutorial.zookeeper.demo;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liaowenhui
 * @date 2016/8/23 18:53.
 */
public class ExampleClient extends LeaderSelectorListenerAdapter implements Closeable {

    private final String name;
    private final LeaderSelector leaderSelector;
    private final AtomicInteger leaderCount = new AtomicInteger();

    public ExampleClient(CuratorFramework client, String path, String name) {
        this.name = name;
        leaderSelector = new LeaderSelector(client, path, this);
        leaderSelector.autoRequeue();
    }

    public void start() throws IOException {
        leaderSelector.start();
    }

    @Override
    public void close() {
        leaderSelector.close();
    }

    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        final int waitSeconds = (int) (5 * Math.random()) + 1;
        System.out.println(name + " is now the leader. Waiting " + waitSeconds + " seconds...");
        System.out.println(name + " has been leader " + leaderCount.getAndIncrement() + " time(s) before.");
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(waitSeconds));
        } catch (InterruptedException e) {
            System.err.println(name + " was interrupted.");
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(name + " relinquishing leadership.\n");
        }
    }

    public static void main(String[] args) throws Exception {

        int CLIENT_QTY = 10;
        String PATH = "/examples/leader";

        List<CuratorFramework> clients = Lists.newArrayList();
        List<ExampleClient> examples = Lists.newArrayList();
        TestingServer server = new TestingServer();
        try {
            for (int i = 0; i < CLIENT_QTY; ++i) {
                CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
                clients.add(client);
                ExampleClient example = new ExampleClient(client, PATH, "Client #" + i);
                examples.add(example);
                client.start();
                example.start();
            }

            System.out.println("Press enter/return to quit\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Shutting down...");
            for (ExampleClient exampleClient : examples) {
                CloseableUtils.closeQuietly(exampleClient);
            }
            for (CuratorFramework client : clients) {
                CloseableUtils.closeQuietly(client);
            }
            CloseableUtils.closeQuietly(server);
        }
    }
}
