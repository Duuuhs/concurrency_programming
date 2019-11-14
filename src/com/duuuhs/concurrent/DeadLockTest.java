package com.duuuhs.concurrent;

/**
 *  测试死锁场景
 *  demo源自：《Java并发编程艺术》
 */
public class DeadLockTest {
    //定义了两把锁
    public static String A =  "A";
    public static String B =  "B";
    public void deadLock(){
        //thread1设计为先后拿到A，B锁
        Thread thread1 = new Thread(new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (A){
                    try {
                        //睡眠3s是为了让另一个线程能抢到B锁
                        System.out.println(Thread.currentThread().getName()+"拿到了A锁");
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (B){
                        System.out.println(Thread.currentThread().getName()+"拿到了B锁");
                    }

                }
            }
        }));
        //thread2设计为先后拿到B，A锁
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (B){
                    try {
                        System.out.println(Thread.currentThread().getName()+"拿到了B锁");
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (A){
                        System.out.println(Thread.currentThread().getName()+"拿到了A锁");
                    }
                }
            }
        });
        thread1.start();
        thread2.start();
    }

    public static void main(String[] args) {
        new DeadLockTest().deadLock();
    }
}
