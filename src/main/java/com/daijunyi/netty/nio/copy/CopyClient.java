package com.daijunyi.netty.nio.copy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author djy
 * @createTime 2022/11/12 00:33
 * @description
 */
public class CopyClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1",8000));
        FileInputStream fileInputStream = new FileInputStream("/Users/djy/Desktop/1234.pdf");
        FileChannel channel = fileInputStream.getChannel();
        long start = System.currentTimeMillis();
        long transferCount = channel.transferTo(0, channel.size(), socketChannel);
        long end = System.currentTimeMillis();
        System.out.println(new StringBuffer("发送字节数：").append(transferCount).append(",耗时：").append(end-start));
    }
}
