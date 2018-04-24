package com.cold.tutorial.concurrent.lock;

import com.cold.tutorial.concurrent.entity.BankCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 使用ReadWriteLock ： 观察者
 * Created by hui.liao on 2015/9/7.
 */
public class ReadWriteLockOB implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(ReadWriteLockOB.class);

    private BankCard bankCard;
    private ReadWriteLock readWriteLock;

    private volatile boolean isStop = false;

    public ReadWriteLockOB(BankCard bankCard, ReadWriteLock readWriteLock) {
        this.bankCard = bankCard;
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void run() {
        try {
            while (!isStop) {
                readWriteLock.readLock().lock();
                int balance = bankCard.getBalance();
                log.info("【{}】-->查看余额 【{}】", Thread.currentThread().getName(), balance);
                readWriteLock.readLock().unlock();
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void stop() {
        isStop = true;
    }
}
