package com.daijunyi.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author djy
 * @createTime 2022/11/11 13:10
 * @description
 */
public class NIOGroupChatClient {

    public static void main(String[] args) {
        NIOGroupChatClient nioGroupChatClient = new NIOGroupChatClient("127.0.0.1", 8000);
        new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                nioGroupChatClient.read();
            }
        }).start();
        nioGroupChatClient.send();
    }

    private Selector selector;

    private SocketChannel socketChannel;

    private String ip;

    private Integer port;

    public NIOGroupChatClient(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(ip,port));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ,ByteBuffer.allocate(1024));
            connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void connect() {
        try {
            SocketAddress localAddress = socketChannel.getLocalAddress();
            System.out.println("客户端："+localAddress.toString()+"准备好了");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        try {
            if (selector.select() > 0){
                //有可用通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()){
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                        byteBuffer.clear();
                        int read = channel.read(byteBuffer);
                        if (read > 0){
                            System.out.println(new String(byteBuffer.array(),0,read));
                        }
                    }
                    iterator.remove();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            try {
                socketChannel.write(ByteBuffer.wrap(scanner.nextLine().getBytes()));
            } catch (IOException e) {
                System.out.println("服务器链接丢失");
            }
        }
        scanner.close();
    }
}
