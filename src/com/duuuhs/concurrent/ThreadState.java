package com.duuuhs.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * 验证线程的几种状态
 * demo源自：《Java并发编程艺术》
 */
public class ThreadState {
    public static void main(String[] args) {
        new Thread(new Sleeping(), "Sleeping Thread").start();
        new Thread(new Waiting(), "Waiting Thread").start();
        //使用两个Blocked线程，一个获取锁成功，另一个获取失败被阻塞
        new Thread(new Blocked(), "Blocked Thread1").start();
        new Thread(new Blocked(), "Blocked Thread2").start();
    }
    //该线程不断的进行睡眠
    static class Sleeping implements Runnable{
        @Override
        public void run() {
            while (true){
                System.out.println(Thread.currentThread().getName()+" now in Sleeping.class");
                SleepingUtils.seconds(1);
            }
        }
    }
    //该线程在Waiting.class实例上等待
    static class Waiting implements Runnable{
        @Override
        public void run() {
            while (true){
                synchronized (Waiting.class){
                    try {
                        System.out.println(Thread.currentThread().getName()+" now in Waiting.class");
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    //该线程在Blocked.class实例上加锁后，不会释放该锁
    static class Blocked implements Runnable{
        @Override
        public void run() {
            synchronized (Blocked.class){
                while (true){
                    System.out.println(Thread.currentThread().getName()+" now in Blocked.class");
                    SleepingUtils.seconds(1);
                }
            }
        }
    }
    //设置线程睡眠的工具类
    static class SleepingUtils{
        static final void seconds(long seconds){
            try {
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /** 可以观察到,Waiting.class处于等待唤醒状态,Sleeping与Blocked1隔1s就打印一次,而Blocked2因为获取不到锁被阻塞了
     * Waiting Thread now in Waiting.class
     * Sleeping Thread now in Sleeping.class
     * Blocked Thread1 now in Blocked.class
     * Blocked Thread1 now in Blocked.class
     * Sleeping Thread now in Sleeping.class
     * Blocked Thread1 now in Blocked.class
     * Sleeping Thread now in Sleeping.class
     * Blocked Thread1 now in Blocked.class
     * Sleeping Thread now in Sleeping.class
     */
}
