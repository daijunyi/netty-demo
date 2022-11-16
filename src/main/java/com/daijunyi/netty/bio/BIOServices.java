package com.daijunyi.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author djy
 * @createTime 2022/11/8 15:39
 * @description
 */
public class BIOServices {

    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("服务器启用了");
        while (true){
            System.out.println("等待连接");
            Socket accept = serverSocket.accept();
            executorService.execute(()->{
                InputStream inputStream = null;
                try {
                    System.out.println(new StringBuffer("建立连接，线程ID：").append(Thread.currentThread().getId()));
                    inputStream = accept.getInputStream();
                    byte[] bytes = new byte[1024];
                    int length = 0;
                    System.out.println("等待接收数据");
                    while ((length = inputStream.read(bytes)) != -1){
                        System.out.println(new StringBuffer("建立连接，线程ID：").append(Thread.currentThread().getId()).append("接收到数据：").append(new String(bytes,0,length)));
                    }
                } catch (IOException e) {

                }finally {
                    System.out.println(Thread.currentThread().getId()+"接收完毕");
                    if (inputStream !=null){
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

            });
        }
    }
}
