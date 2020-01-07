package com.duuuhs.concurrent;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 管道输入/输出
 * demo源自：《Java并发编程艺术》
 */
public class Piped {
    public static void main(String[] args) throws IOException {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        //将输入输出流进行连接,否则出现IO异常
        out.connect(in);
        Thread thread = new Thread(new Print(in), "PrintThread");
        thread.start();
        int receive = 0;
        try {
            while ((receive = System.in.read()) != -1){
                out.write(receive);
            }
        } finally {
            out.close();
        }
    }

    //管道输入,并进行打印
    static class Print implements Runnable{
        private PipedReader in;
        public Print(PipedReader in){
            this.in = in;
        }
        @Override
        public void run() {
            int receive = 0;
            while (true){
                try {
                    while ((receive = in.read()) != -1) {
                        System.out.println((char) receive);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 控制台输入： Hello, 打印如下：
     * H
     * e
     * l
     * l
     * o
     */
}
