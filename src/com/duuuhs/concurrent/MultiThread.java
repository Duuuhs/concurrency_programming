package com.duuuhs.concurrent;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 *  利用JMX来查看一个普通的Java程序包含哪些线程
 *  demo源自：《Java并发编程艺术》
 */
public class MultiThread {
    public static void main(String[] args) {
        //获取Java线程管理MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //不需要获取同步的monitor和synchronizer信息,仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        //遍历线程信息,仅仅打印线程id与线程name
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "]" + threadInfo.getThreadName());
        }
    }

    /**
     * [6]Monitor Ctrl-Break
     * [5]Attach Listener
     * [4]Signal Dispatcher
     * [3]Finalizer
     * [2]Reference Handler
     * [1]main
     */
}
