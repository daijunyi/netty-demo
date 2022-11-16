package com.daijunyi.netty.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author djy
 * @createTime 2022/11/9 14:22
 * @description
 */
public class ChannelBufferWriter {

    public static void main(String[] args) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("/Users/djy/Desktop/1.txt");
            FileChannel channel = fileOutputStream.getChannel();

            ByteBuffer allocate = ByteBuffer.allocate(1024);
            allocate.put("hello word".getBytes());
            allocate.flip();
            channel.write(allocate);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
