package com.duuuhs.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 *  多线程使用CountDownLatch
 *  demo源自：互联网
 */
public class CountDownDemo {
    public static void main(String[] args) {
        try {
            CountDownLatchService service = new CountDownLatchService();
            CountDownLatch begin = new CountDownLatch(1);//裁判员鸣枪信号
            CountDownLatch end = new CountDownLatch(10);//10名参赛选手结束信号
            CountDownLatchThread[] threadArray = new CountDownLatchThread[10];
            for(int i = 0 ; i < 10; i++){
                threadArray[i] = new CountDownLatchThread(service,begin,end);
                threadArray[i].setName((i + 1) + " 号选手 ");
                threadArray[i].start();
            }
            System.out.println("在等待裁判员鸣枪  " + System.currentTimeMillis());
            long t1 = System.currentTimeMillis();//记录比赛的开始时间
            begin.countDown();//裁判员鸣枪了
            end.await();//等待10个参赛选手都跑完100米
            long t2 = System.currentTimeMillis();//记录比赛的结束时间
            System.out.println("所有参赛选手都完成了100米赛跑，赛跑总耗时是  " + (t2-t1));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 在等待裁判员鸣枪  1579617823231
     * 7 号选手  begin timer 1579617823232
     * 1 号选手  begin timer 1579617823232
     * 3 号选手  begin timer 1579617823233
     * 5 号选手  begin timer 1579617823235
     * 9 号选手  begin timer 1579617823235
     * 4 号选手  begin timer 1579617823236
     * 8 号选手  begin timer 1579617823236
     * 2 号选手  begin timer 1579617823236
     * 6 号选手  begin timer 1579617823236
     * 10 号选手  begin timer 1579617823237
     * 2 号选手  end timer 1579617824052
     * 5 号选手  end timer 1579617825643
     * 9 号选手  end timer 1579617825877
     * 3 号选手  end timer 1579617826130
     * 7 号选手  end timer 1579617826997
     * 4 号选手  end timer 1579617828060
     * 1 号选手  end timer 1579617828314
     * 8 号选手  end timer 1579617828376
     * 6 号选手  end timer 1579617831857
     * 10 号选手  end timer 1579617832117
     * 所有参赛选手都完成了100米赛跑，赛跑总耗时是  8885
     */

}
