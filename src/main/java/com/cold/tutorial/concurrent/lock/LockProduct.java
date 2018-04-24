package com.cold.tutorial.concurrent.lock;

import com.cold.tutorial.concurrent.entity.BankCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 生产类，负责赚钱
 * Created by hui.liao on 2015/9/7.
 */
public class LockProduct implements Runnable{

    private static final Logger log = LoggerFactory.getLogger(LockProduct.class);

    private BankCard bankCard;
    private Lock lock;

    private volatile boolean isStop = false;

    public LockProduct(BankCard bankCard, Lock lock) {
        this.bankCard = bankCard;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            while (!isStop) {
                lock.lock();
                int balance = bankCard.getBalance();
                log.info("【{}】-->生产类开始赚钱，余额 【{}】", Thread.currentThread().getName(), balance);
                bankCard.setBalance(balance + 20000);
                log.info("【{}】-->赚钱20000元，剩余余额 【{}】", Thread.currentThread().getName(), bankCard.getBalance());
                lock.unlock();
                TimeUnit.SECONDS.sleep(3);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        isStop = true;
    }
}
