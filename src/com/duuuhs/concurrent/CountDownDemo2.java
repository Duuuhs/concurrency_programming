package com.duuuhs.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 *  CountDownLatch的例子2
 *  demo源自：《Java并发编程艺术》
 */
public class CountDownDemo2 {
    static CountDownLatch cdl = new CountDownLatch(2);
    public static void main(String[] args) throws InterruptedException {
        System.out.println("start......");
        long start = System.currentTimeMillis();
        cdl.countDown();
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                cdl.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        cdl.await();
        long end = System.currentTimeMillis();
        System.out.println("end......");
        System.out.println("耗时:" + (end-start));
    }

    /**
     * start......
     * end......
     * 耗时:5116
     */

}
