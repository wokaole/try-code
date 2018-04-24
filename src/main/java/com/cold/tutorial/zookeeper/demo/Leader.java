package com.cold.tutorial.zookeeper.demo;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author liaowenhui
 * @date 2016/8/23 17:37.
 */
public class Leader {

    private static final int CLIENT_QTY = 10;
    private static final String PATH = "/examples/leader";

    public static void main(String[] args) throws Exception {
        List<CuratorFramework> clients = Lists.newArrayList();
        List<LeaderLatch> examples = Lists.newArrayList();
        TestingServer server = new TestingServer();

        try {
            IntStream.range(0, CLIENT_QTY).forEach(i -> {
                CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
                clients.add(client);
                LeaderLatch example = new LeaderLatch(client, PATH, "client # " + i);
                examples.add(example);
                client.start();
                try {
                    example.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            Thread.sleep(20000);

            LeaderLatch currentLeader = null;
            for (int i = 0; i < CLIENT_QTY; ++i) {
                LeaderLatch example = examples.get(i);
                if (example.hasLeadership())
                    currentLeader = example;
            }
            System.out.println(examples.get(0).getLeader().getId());
            System.out.println("current leader is " + currentLeader.getId());
            System.out.println("release the leader " + currentLeader.getId());
            currentLeader.close();
            examples.get(0).await(2, TimeUnit.SECONDS);
            System.out.println("Client #0 maybe is elected as the leader or not although it want to be");
            System.out.println("the new leader is " + examples.get(0).getLeader().getId());

            System.out.println("Press enter/return to quit\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Shutting down...");
            examples.forEach(CloseableUtils::closeQuietly);
            clients.forEach(CloseableUtils::closeQuietly);
            CloseableUtils.closeQuietly(server);
        }
    }

}
