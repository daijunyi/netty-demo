package com.daijunyi.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author djy
 * @createTime 2022/11/10 20:12
 * @description
 */
public class NIOServer {

    public static void main(String[] args) {
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();

            //绑定接口
            serverSocketChannel.socket().bind(new InetSocketAddress(8000));
            Selector selector = Selector.open();
            //非阻塞
            serverSocketChannel.configureBlocking(false);
            //注册selector到serverSocketChannel中，关心的事件是接受信息
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                if (selector.select(1000) == 0) {
                    System.out.println("等待了1秒为接收到接入信息");
                    continue;
                }
                Thread.sleep(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    }

                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        channel.configureBlocking(false);
                        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                        int read = 0;
                        while ((read = channel.read(byteBuffer)) != -1 && read != 0) {
                            System.out.println("接收到信息");
                            System.out.println(new String(byteBuffer.array(), 0, read));
                            byteBuffer.clear();
                        }
                    }
                    System.out.println("选项"+key.readyOps());
                    iterator.remove();
                }
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (serverSocketChannel != null) {
                    serverSocketChannel.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
