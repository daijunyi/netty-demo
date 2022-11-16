package com.daijunyi.netty.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author djy
 * @createTime 2022/11/9 14:38
 * @description
 */
public class ChannelBufferReader {

    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("/Users/djy/Desktop/1.txt");
            FileChannel channel = fileInputStream.getChannel();
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            int read = 0;
            while ((read = channel.read(allocate)) != -1){
                System.out.println(new String(allocate.array(),0,read));
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
