package com.duuuhs.concurrent;

import java.util.concurrent.TimeUnit;

/**
 *  测试中断操作
 *  demo源自：《Java并发编程艺术》
 */
public class Interrupted {
    public static void main(String[] args) {
        Thread busyThread = new Thread(new BusyRunner(), "busyThread");
        Thread sleepThread = new Thread(new SleepRunner(), "sleepThread");
        busyThread.setDaemon(true);
        sleepThread.setDaemon(true);
        busyThread.start();
        sleepThread.start();
        //让两个线程充分预热执行
        SleepingUtils.seconds(5);
        //中断两个线程
        busyThread.interrupt();
        sleepThread.interrupt();
        //打印两个线程的状态
        System.out.println("busyThread isInterrupted(): " + busyThread.isInterrupted());
        System.out.println("sleepThread isInterrupted(): " + sleepThread.isInterrupted());
        //防止两个线程立即退出,原因是两个线程都设置了Daemon属性
        SleepingUtils.seconds(5);
    }

    //BusyRunner一直执行
    static class BusyRunner implements Runnable{
        @Override
        public void run() {
            while (true){

            }
        }
    }
    //SleepRunner一直睡眠
    static class SleepRunner implements Runnable{
        @Override
        public void run() {
            while (true){
                SleepingUtils.seconds(1);
            }
        }
    }
    //设置线程睡眠的工具类
    static class SleepingUtils {
        static final void seconds(long seconds) {
            try {
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>在执行完SleepingUtils.seconds(5)后线程均退出</>
     * busyThread isInterrupted(): true
     * sleepThread isInterrupted(): false
     * java.lang.InterruptedException: sleep interrupted
     * 	at java.lang.Thread.sleep(Native Method)
     * 	at java.lang.Thread.sleep(Thread.java:340)
     * 	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
     * 	at com.duuuhs.concurrent.Interrupted$SleepingUtils.seconds(Interrupted.java:51)
     * 	at com.duuuhs.concurrent.Interrupted$SleepRunner.run(Interrupted.java:43)
     * 	at java.lang.Thread.run(Thread.java:745)
     */
}
