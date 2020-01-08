package com.duuuhs.concurrent;

/**
 * Thread.join
 * demo源自：《Java并发编程艺术》
 */
public class Join {
    public static void main(String[] args) {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i ++){
            //每个线程拥有前一个线程的引用, 需要等待前一个线程终止,才能从等待中返回
            Thread thread = new Thread(new Domino(previous), String.valueOf(i));
            thread.start();
            previous = thread;
        }
        System.out.println(Thread.currentThread().getName() + " terminate...");
    }
    static class Domino implements Runnable{
        private Thread thread;
        public Domino(Thread thread){
            this.thread = thread;
        }
        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " terminate...");
        }
    }

    /**
     * 前后两个线程互为父子线程,子线程join结束之后, 父线程从wait()状态中唤醒
     * main terminate...
     * 0 terminate...
     * 1 terminate...
     * 2 terminate...
     * 3 terminate...
     * 4 terminate...
     * 5 terminate...
     * 6 terminate...
     * 7 terminate...
     * 8 terminate...
     * 9 terminate...
     */
}
