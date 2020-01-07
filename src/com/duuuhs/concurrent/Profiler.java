package com.duuuhs.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal
 * demo源自：《Java并发编程艺术》
 */
public class Profiler {
    //第一次get()方法的时候会进行初始化(如果set方法没有被调用),每个线程会调用一次
    public static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>(){
        protected Long initialValue(){
            return System.currentTimeMillis();
        }
    };
    public static final void begin(){
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }
    public static final long end(){
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }
    public static void main(String[] args) throws InterruptedException {
        Profiler.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Cost： " + Profiler.end() + " mills");
    }

    /**
     * Cost： 1001 mills
     */
}
