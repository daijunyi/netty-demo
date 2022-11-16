package com.daijunyi.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author djy
 * @createTime 2022/11/10 22:47
 * @description
 */
public class NIOClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        if (!socketChannel.connect(new InetSocketAddress("127.0.0.1",8000))){
            while (!socketChannel.finishConnect()){
                System.out.println("链接需要时间，客户端不会阻塞，可以做其他事情");
            }
        }
        for (int i=0;i<10;i++){
            byte[] bytes = new StringBuffer("我正在发送数据").append(i).toString().getBytes();
            socketChannel.write(ByteBuffer.wrap(bytes));
        }
        System.in.read();
    }
}
