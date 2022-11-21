package com.daijunyi.netty.netty.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author djy
 * @createTime 2022/11/21 12:19
 * @description
 */
public class MyClient {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class).handler(new MyClientChannelInitializer());
        ChannelFuture cf = bootstrap.connect("127.0.0.1", 8000).sync();
        cf.channel().closeFuture().sync();
    }
}
