package com.cold.tutorial.concurrent.lock;

import com.cold.tutorial.concurrent.entity.BankCard;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Lock类的消费类: 消费钱
 * Created by hui.liao on 2015/9/7.
 */
public class LockConsumer implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(LockConsumer.class);

    private BankCard bankCard;
    private Lock lock;

    private volatile boolean isStop = false;

    public LockConsumer(BankCard bankCard, Lock lock) {
        this.bankCard = bankCard;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            while (!isStop) {
                lock.lock();
                int balance = bankCard.getBalance();
                log.info("【{}】-->消费类开始消费，余额 【{}】", Thread.currentThread().getName(), balance);
                bankCard.setBalance(balance - 20000);
                log.info("【{}】-->消费20000元，剩余余额 【{}】", Thread.currentThread().getName(), bankCard.getBalance());
                lock.unlock();
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        isStop = true;
    }

    public static void main(String[] args) {

        try {
            BankCard bankCard = new BankCard();
            Lock lock = new ReentrantLock();

            ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("lock-%d").build();

            ExecutorService service = Executors.newFixedThreadPool(2, threadFactory);
            LockProduct product = new LockProduct(bankCard, lock);
            LockConsumer consumer = new LockConsumer(bankCard, lock);
            service.execute(product);
            service.execute(consumer);
            TimeUnit.SECONDS.sleep(10);
            product.stop();
            TimeUnit.SECONDS.sleep(10);
            consumer.stop();

            service.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
