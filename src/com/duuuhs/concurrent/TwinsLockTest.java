package com.duuuhs.concurrent;

import java.util.concurrent.locks.Lock;

/**
 * 自定义同步组件-测试
 * demo源自：《Java并发编程艺术》
 */
public class TwinsLockTest {

    public static final Lock lock = new TwinsLock();

    static class Worker implements Runnable{
        @Override
        public void run() {
            while (true){
                lock.lock();
                try {
                    ThreadState.SleepingUtils.seconds(1);
                    System.out.println("....");
                    System.out.println(Thread.currentThread().getName());
                    ThreadState.SleepingUtils.seconds(1);
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        for (int i=0; i<11; i++){
            Thread thread = new Thread(new Worker(), "线程"+i);
            thread.setDaemon(true);
            thread.start();
        }
        //每隔1s换行
        for (int i=0; i<11; i++){
            ThreadState.SleepingUtils.seconds(1);
            System.out.println("");
        }
    }
}
