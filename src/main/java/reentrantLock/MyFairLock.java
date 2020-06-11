package reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁，哪个线程先启动就先获得锁或者是哪个线程等待最久就先获得锁
 */
public class MyFairLock {
    /**
     * true 表示 ReentrantLock 的公平锁
     */
    private ReentrantLock lock = new ReentrantLock(true);

    public void testFail() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获得了锁");
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName()+"释放了锁");
        }
    }

    public static void main(String[] args) {
        MyFairLock fairLock = new MyFairLock();
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + "启动");
            fairLock.testFail();
        };
        Thread[] threadArray = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threadArray[i] = new Thread(runnable);
        }
        for (int i = 0; i < 10; i++) {
            threadArray[i].start();
        }
    }
}
