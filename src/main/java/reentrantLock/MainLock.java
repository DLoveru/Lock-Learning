package reentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainLock {
    private Lock lock = new ReentrantLock();
    public static void main(String[] args) {
        final MainLock mainLock = new MainLock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mainLock.insert(Thread.currentThread());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mainLock.insert(Thread.currentThread());
            }
        }).start();
    }

    public void insert(Thread thread){
        lock.lock();//获取锁
        try{
            System.out.println(thread.getName() + "获取锁");
            for(int i=0;i<5;i++){
                System.out.println("------------------------" + thread.getName() + ":"+i+"------------------------");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();//释放锁
            System.out.println(thread.getName() + "释放锁");
        }
    }
}