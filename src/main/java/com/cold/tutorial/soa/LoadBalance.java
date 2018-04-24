package com.cold.tutorial.soa;

import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author hui.liao
 *         2016/1/18 17:40
 */
public class LoadBalance {

    private static Map<String, Integer> serverMap = new HashMap<>();
    private static AtomicInteger pos = new AtomicInteger(0);

    static {
        serverMap.put("192.168.0.1", 1);
        serverMap.put("192.168.0.2", 1);
        serverMap.put("192.168.1.1", 2);
        serverMap.put("192.168.2.1", 3);
        serverMap.put("192.168.3.1", 4);

    }

    //轮询法
    public static String testRoundRobin() {
        Map<String, Integer> serverMap1 = new HashMap<>();
        serverMap1.putAll(serverMap);

        Set<String> keySet = serverMap1.keySet();
        List<String> list = new ArrayList<>();
        list.addAll(keySet);

        String server;
        synchronized (pos) {
            int i = pos.get();
            if (i >= list.size()) {
                pos.set(0);
            }
            int index = pos.getAndIncrement();
            System.out.println(index);
            server = list.get(index);
        }

        return server;
    }

    //随机法，吞吐量越大越接近轮询法
    public static String testRandom() {
        Map<String, Integer> serverMap1 = new HashMap<>();
        serverMap1.putAll(serverMap);

        Set<String> keySet = serverMap1.keySet();
        List<String> list = new ArrayList<>();
        list.addAll(keySet);

        Random random = new Random();
        int index = random.nextInt(list.size());

        String server = list.get(index);
        return server;
    }

    public static String testHash(String remoteIp) {
        Map<String, Integer> serverMap1 = new HashMap<>();
        serverMap1.putAll(serverMap);

        Set<String> keySet = serverMap1.keySet();
        List<String> list = new ArrayList<>();
        list.addAll(keySet);

        int hashCode = remoteIp.hashCode();
        int index = hashCode % list.size();

        String server = list.get(index);
        return server;
    }

    public static String testWeightRoundRobin() {
        Map<String, Integer> serverMap1 = new HashMap<>();
        serverMap1.putAll(serverMap);

        Set<Map.Entry<String, Integer>> entries = serverMap1.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = entries.iterator();

        List<String> list = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> next = iterator.next();
            int weight = next.getValue();
            String key = next.getKey();
            for (int i=0; i<weight; i++) {
                list.add(key);
            }
        }

        serverMap1.forEach((key, Value) -> {
            for (int i=0; i<Value; i++) {
                list.add(key);
            }
        });

        String server;
        synchronized (pos) {
            int i = pos.get();
            if (i >= list.size()) {
                pos.set(0);
            }
            int index = pos.getAndIncrement();
            System.out.println(index);
            server = list.get(index);
        }

        return server;
    }

    public static String testWeightRandom() {
        Map<String, Integer> serverMap1 = new HashMap<>();
        serverMap1.putAll(serverMap);

        List<String> list = new ArrayList<>();
        serverMap1.forEach((key, Value) -> {
            for (int i=0; i<Value; i++) {
                list.add(key);
            }
        });

        Random random = new Random();
        int index = random.nextInt(list.size());

        String server = list.get(index);
        return server;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        IntStream.range(0, 10).forEach((i)-> pool.execute(() -> {
            String s = testWeightRoundRobin();
            System.out.println(s);
        }));

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }
}
