package reentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁，响应中断
 * tryLock 是防止自锁的一个重要方式。
 * tryLock()方法是有返回值的，它表示用来尝试获取锁，如果获取成功，则返回true，
 * 如果获取失败（即锁已被其他线程获取），
 * 则返回false，这个方法无论如何都会立即返回。在拿不到锁时不会一直在那等待
 *
 */
public class TryLockTest {
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    public static void main(String[] args)
            throws InterruptedException {
        Thread thread = new Thread(new ThreadDemo(lock1, lock2));
        Thread thread1 = new Thread(new ThreadDemo(lock2, lock1));
        thread.start();
        thread1.start();
    }

    static class ThreadDemo implements Runnable {
        Lock firstLock;
        Lock secondLock;

        public ThreadDemo(Lock firstLock, Lock secondLock) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }

        @Override
        public void run() {
            lock1.lock();
            try {
                if (!lock1.tryLock()) {
                    TimeUnit.SECONDS.sleep(2);
                }
                if (!lock2.tryLock()) {
                    TimeUnit.SECONDS.sleep(2);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                firstLock.unlock();
                secondLock.unlock();
                System.out.println(Thread.currentThread().getName()
                        + "获取到了资源，正常结束!");
            }
        }
    }
}
