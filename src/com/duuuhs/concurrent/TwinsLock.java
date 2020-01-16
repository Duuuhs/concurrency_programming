package com.duuuhs.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义同步组件
 * demo源自：《Java并发编程艺术》
 */
public class TwinsLock implements Lock {

    private final MySync sync = new MySync(2);

    private static final class MySync extends AbstractQueuedSynchronizer{
        /**
         * @param count 共享式同步器最大的同时访问的资源数
         */
        MySync(int count){
            if (count <= 0){
                throw new IllegalArgumentException("count must large than zero!");
            }
            setState(count);
        }

        /**
         * 共享式同步状态获取
         * @param reduceCount
         * @return
         */
        public int tryAcquireShared(int reduceCount){
            for (;;){
                int current = getState();
                int newCount = current = reduceCount;
                //判断获取同步状态的线程不超过2个, 且CAS确保安全获取同步状态
                if (newCount < 0 || compareAndSetState(current, newCount)){
                    return newCount;
                }
            }
        }

        /**
         * 共享式同步状态释放
         * @param reduceCount
         * @return
         */
        public boolean tryReleaseShared(int reduceCount){
            for (;;){
                int current = getState();
                int newCount = current + reduceCount;
                if (compareAndSetState(current, newCount)){
                    return true;
                }
            }
        }
    }
    @Override
    public void lock() {
        sync.tryAcquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.tryReleaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
