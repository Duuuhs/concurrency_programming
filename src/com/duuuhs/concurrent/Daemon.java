package com.duuuhs.concurrent;

/**
 * 验证守护线程是否会执行finally代码块
 * demo源自：《Java并发编程艺术》
 */
public class Daemon {
    static class DaemonThread implements Runnable{
        @Override
        public void run() {
            try {
                System.out.println("DaemonThread try...");
            }finally {
                System.out.println("DaemonThread finally...");
            }
        }
    }
    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonThread(), "DaemonThread");
        thread.setDaemon(true);
        thread.start();
    }
    /**
     * 控制态输出如下：
     * DaemonThread try...
     *
     * 因此守护线程Daemon不执行finally代码块
     */
}
