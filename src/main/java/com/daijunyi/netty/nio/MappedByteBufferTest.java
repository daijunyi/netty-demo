package com.daijunyi.netty.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author djy
 * @createTime 2022/11/9 16:17
 * @description
 */
public class MappedByteBufferTest {
    public static void main(String[] args) {

        RandomAccessFile rw = null;
        try {
            rw = new RandomAccessFile("/Users/djy/Desktop/1.txt", "rw");
            FileChannel channel = rw.getChannel();
            /**
             * 参数1：FileChannel.MapMode.READ WRITE
             * 使用的读写模式
             * 参数2：日：可以直接修改的起始位置
             * 参数3：20：是映射到内存的列小，即将1.txt的多少个字节映射到内存米
             * 可以直接修改的范围就是0-10
             * *实际类型DirectByteBuffer
             */
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 20);
            map.put(0, (byte) 'a');
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (rw != null){
                try {
                    rw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
