package com.duuuhs.concurrent;

/**
 *  测试单线程与多线程的不同
 *  硬件信息：Intel i5-7200u 四核心数
 *  demo源自：《Java并发编程艺术》
 */
public class ConcurrencyTest {

    private static final long count = 1000000000;

    /**
     * 单线程，用的是psvm的主线程
     * @throws InterruptedException
     */
    public static void serial() throws InterruptedException{
        long start = System.currentTimeMillis();
        long a = 0;
        for (int i=0;i<count;i++){
            a+=5;
        }
        long b = 0;
        for (int j=0;j<count;j++){
            b+=5;
        }
        System.out.println("serial() execute:" + (System.currentTimeMillis()- start));
    }

    /**
     * 多线程，用的是psvm的主线程外加一个自定义的Thread，并用Thread.join()来控制。
     * @throws InterruptedException
     */
    public static void concurrency() throws InterruptedException{
        long start = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                long a = 0;
                for (int i=0;i<count;i++){
                    a+=5;
                }
            }
        });
        thread.start();
        long b = 0;
        for (int j=0;j<count;j++){
            b+=5;
        }
        thread.join();
        System.out.println("concurrency() execute:" + (System.currentTimeMillis()- start));
    }

    public static void main (String[] args) throws InterruptedException{
        concurrency();
        serial();

        /**
         * count    serial()  concurrency()
         * 1000w      23ms        51ms
         *  1e       196ms       200ms
         *  10e     1902ms      1554ms
         */
    }
}