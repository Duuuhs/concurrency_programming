package com.duuuhs.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *  单线程测试CyclicBarrier
 *  demo源自：《Java并发编程艺术》
 */
public class CyclicBarrierSingle {

    static CyclicBarrier cb = new CyclicBarrier(2, new A());

    /**
     * 计数器为0时触发的Runnable类中的run方法
     */
    static class A implements Runnable{
        @Override
        public void run() {
            System.out.println("3");
        }
    }
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                cb.await();
                System.out.println("1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            cb.await();
            System.out.println("2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出为:
     * 3
     * 1
     * 2
     */
}
