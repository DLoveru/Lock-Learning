package com.lock.spin;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 自旋锁测试类
 * countDownLatch这个类使一个线程等待其他线程各自执行完毕后再执行。
 * 是通过一个计数器来实现的，计数器的初始值是线程的数量。
 * 每当一个线程执行完毕后，计数器的值就-1，当计数器的值为0时，表示所有线程都执行完毕，
 * 然后在闭锁上等待的线程就可以恢复工作了
 *
 * 
 * @author onlyone
 */
public class SpinLockTest implements Runnable {

    private SpinLock       spinLock;
    private CountDownLatch countDownLatch;

    public SpinLockTest(SpinLock spinLock, CountDownLatch countDownLatch){
        this.spinLock = spinLock;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // 尝试获取锁
        spinLock.lock();

        String name = Thread.currentThread().getName();
        System.out.println(name + " 已经获得锁！");
        // 模拟业务处理
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 释放锁
        System.out.println(name + " 处理完毕，并释放锁");
        spinLock.unlock();

    }

    public static void main(String[] args) throws InterruptedException {
        SpinLock spinLock = new SpinLock();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        SpinLockTest task = new SpinLockTest(spinLock, countDownLatch);

        for (int i = 1; i <= 10; i++) {
            new Thread(task).start();
        }
        countDownLatch.countDown();

        // 主线程阻塞，防止jvm提早退出
        Thread.sleep(150000);

    }

}
