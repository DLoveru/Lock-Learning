package reentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁，响应中断
 * 响应中断就是一个线程获取不到锁，不会傻傻的一直等下去，ReentrantLock会给与一个中断回应
 */
public class ResponseinterruptDemo {
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    public static void main(String[] args)
            throws InterruptedException {
        Thread thread = new Thread(new ThreadDemo(lock1, lock2));
        Thread thread1 = new Thread(new ThreadDemo(lock2, lock1));
        thread.start();
        thread1.start();
        thread.interrupt();//是第一个线程中断
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
            try {
                firstLock.lockInterruptibly();
                TimeUnit.MILLISECONDS.sleep(50);
                secondLock.lockInterruptibly();
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
