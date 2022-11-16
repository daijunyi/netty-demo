package com.daijunyi.netty.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author djy
 * @createTime 2022/11/8 16:21
 * @description
 */
public class BIOClient {

    public static void main(String[] args){
        for (int i=0;i<3;i++){
            new Thread(()->{
                try {
                    Socket socket = new Socket("127.0.0.1", 8000);
                    OutputStream outputStream = socket.getOutputStream();
                    int b = 0;
                    while (b < 3){
                        String msg = new StringBuffer("正在写数据").append(b).toString();
                        outputStream.write(msg.getBytes());
                        System.out.println(msg);
                        b++;
                        Thread.sleep(1000);
                    }
                    System.out.println("发送完毕");
                    outputStream.close();
                    socket.close();
                } catch (Exception e){

                }

            }).start();
        }
    }
}
