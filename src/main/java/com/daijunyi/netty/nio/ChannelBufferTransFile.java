package com.daijunyi.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author djy
 * @createTime 2022/11/9 15:57
 * @description
 */
public class ChannelBufferTransFile {

    public static void main(String[] args) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream("/Users/djy/Desktop/1.txt");
            fos = new FileOutputStream("/Users/djy/Desktop/3.txt");

            FileChannel fisChannel = fis.getChannel();
            FileChannel fosChannel = fos.getChannel();
            fosChannel.transferFrom(fisChannel,0,fisChannel.size());
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
