package com.daijunyi.netty.nio.copy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author djy
 * @createTime 2022/11/11 15:44
 * @description
 */
public class CopyServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8000));
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            int readCount = 0;
            FileOutputStream fileOutputStream = new FileOutputStream("/Users/djy/Desktop/1234_1.pdf");
            FileChannel channel = fileOutputStream.getChannel();
            while ((readCount = socketChannel.read(byteBuffer)) != -1 && readCount != 0){
                byteBuffer.flip();
                channel.write(byteBuffer);
                byteBuffer.clear();
            }
            fileOutputStream.close();
            channel.close();
        }
    }
}
