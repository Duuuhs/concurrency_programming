package com.duuuhs.concurrent;

import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 *  多线程测试CyclicBarrier
 *  demo源自：《Java并发编程艺术》
 */
public class CyclicBarrierConcurrent {

    static final Integer MAX_THREAD = 10;

    static Integer count = 0;

    private final static ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    private static CyclicBarrier cb = new CyclicBarrier(MAX_THREAD, new sum());

    /**
     * 所有线程await后会触发此Runnable计算总和
     */
    static class sum implements Runnable{
        @Override
        public void run() {
            for (Map.Entry<String, Integer> entry : map.entrySet()){
                count += entry.getValue();
            }
            System.out.println("执行结束, 计算结果为: " + count);
        }
    }

    public static void main(String[] args) {
        for (Integer i = 0; i < MAX_THREAD; i++){
            new Thread(() -> {
                int ceil = (int)Math.ceil(Math.random()*10);
                System.out.println(Thread.currentThread().getName() + " -> " + ceil);
                map.put(Thread.currentThread().getName(), ceil);
                try {
                    //一次await()会将计数器减一
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "Thread" + (i+1)).start();
        }
    }

    /**
     * Thread7 -> 4
     * Thread2 -> 6
     * Thread5 -> 2
     * Thread6 -> 6
     * Thread3 -> 10
     * Thread8 -> 5
     * Thread1 -> 10
     * Thread10 -> 2
     * Thread9 -> 2
     * Thread4 -> 7
     * 执行结束, 计算结果为: 54
     */
}
