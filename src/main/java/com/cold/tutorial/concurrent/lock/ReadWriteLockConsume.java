package com.cold.tutorial.concurrent.lock;

import com.cold.tutorial.concurrent.entity.BankCard;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用ReadWriteLock ： 消费者
 * Created by hui.liao on 2015/9/7.
 */
public class ReadWriteLockConsume implements Runnable{
    private static final Logger log = LoggerFactory.getLogger(ReadWriteLockConsume.class);

    private BankCard bankCard;
    private ReadWriteLock readWriteLock;

    private volatile boolean isStop = false;

    public ReadWriteLockConsume(BankCard bankCard, ReadWriteLock readWriteLock) {
        this.bankCard = bankCard;
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void run() {
        try {
            while (!isStop) {
                readWriteLock.writeLock().lock();

                int balance = bankCard.getBalance();
                log.info("【{}】-->消费类开始消费，余额 【{}】", Thread.currentThread().getName(), balance);
                bankCard.setBalance(balance - 20000);
                log.info("【{}】-->消费20000元，剩余余额 【{}】", Thread.currentThread().getName(), bankCard.getBalance());

                readWriteLock.writeLock().unlock();
                TimeUnit.SECONDS.sleep(2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        isStop = true;
    }

    public static void main(String[] args) {

        BankCard bankCard = new BankCard();
        ReadWriteLock lock = new ReentrantReadWriteLock();

        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("readWriteLock - %d").build();
        ExecutorService service = Executors.newFixedThreadPool(3, threadFactory);

        ReadWriteLockConsume consume = new ReadWriteLockConsume(bankCard, lock);

        ReadWriteLockOB ob1 = new ReadWriteLockOB(bankCard, lock);
        ReadWriteLockOB ob2 = new ReadWriteLockOB(bankCard, lock);

        service.submit(consume);
        service.submit(ob1);
        service.submit(ob2);


    }
}
