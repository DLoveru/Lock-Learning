##可重入锁的概念
####ReentrantLock
ReentrantLock翻译过来为可重入锁，它的可重入性表现在同一个线程可以多次获得锁，而不同线程依然不可多次获得锁。
ReentrantLock分为公平锁和非公平锁，公平锁保证等待时间最长的线程将优先获得锁，
而非公平锁并不会保证多个线程获得锁的顺序，但是非公平锁的并发性能表现更好，ReentrantLock默认使用非公平锁。
当然也可以自己设置
public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
}
公平锁实现原理 ReentrantLock.FairSync()

非公平锁实现原理 ReentrantLock.NonfairSync()
公平锁的tryAcquire实现和非公平锁的tryAcquire实现的区别在于：
公平锁多加了一个判断条件：hasQueuedPredecessors
，如果发现有线程在等待获取锁了，那么就直接返回false，
否则在继承尝试获取锁，这样就保证了线程是按照排队时间来有限获取锁的。
而非公平锁的实现则不考虑是否有节点在排队，会直接去竞争锁，
如果获取成功就返回true，否则返回false。


####ReentrantReadWriteLoc
ReentrantReadWriteLock并没有继承ReentrantLock，
也并没有实现Lock接口，而是实现了ReadWriteLock接口，
该接口提供readLock()方法获取读锁，writeLock()获取写锁。
读锁底层分析

写锁底层分析




