package com.daijunyi.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author djy
 * @createTime 2022/11/11 12:36
 * @description
 */
public class NIOGroupChatServer {

    public static void main(String[] args) {
        NIOGroupChatServer nioGroupChatServer = new NIOGroupChatServer();
        nioGroupChatServer.listen();
    }

    private ServerSocketChannel serverSocketChannel;
    private Integer port = 8000;

    private Selector selector;

    public NIOGroupChatServer() {
        init(port);
    }

    public NIOGroupChatServer(Integer port) {
        this.port = port;
        init(port);
    }

    private void init(Integer port) {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            System.out.println("群里服务器启动失败");
            throw new RuntimeException(e);
        }
    }

    public void listen() {
        while (true){
            try {
                if(selector.select(1000) == 0){
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()){
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                        SocketAddress remoteAddress = socketChannel.getRemoteAddress();
                        System.out.println(remoteAddress.toString()+"上线");
                        sendOtherClient("上线了",socketChannel);
                    }

                    if (key.isReadable()){
                        readInfo(key);
                    }

                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readInfo(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
        byteBuffer.clear();
        int read = 0;
        try {
            read = socketChannel.read(byteBuffer);
        } catch (IOException e) {
            try {
                String msg = socketChannel.getRemoteAddress().toString() + ":离线了";
                System.out.println(msg);
                sendOtherClient(msg,socketChannel);
                key.cancel();
                socketChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (read > 0){
            String msg = new String(byteBuffer.array(),0,read);
            sendOtherClient(msg,socketChannel);
        }
    }

    public void sendOtherClient(String msg,SocketChannel exclude) {
        if (msg == null || msg.length() == 0){
            return;
        }
        System.out.println("转发消息:"+msg);
        for (SelectionKey key : selector.keys()) {
            SelectableChannel selectableChannel = key.channel();
            if (selectableChannel instanceof SocketChannel && selectableChannel != exclude){
                SocketChannel socketChannel = (SocketChannel) selectableChannel;
                try {
                    socketChannel.write(ByteBuffer.wrap(new StringBuffer(exclude.getRemoteAddress().toString())
                            .append(":")
                            .append(msg)
                            .toString()
                            .getBytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        System.out.println(socketChannel.getRemoteAddress().toString()+"离线");
                        key.cancel();
                        socketChannel.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

}
