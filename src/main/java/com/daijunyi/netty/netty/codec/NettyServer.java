package com.daijunyi.netty.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

import java.util.ArrayList;

/**
 * @author djy
 * @createTime 2022/11/14 13:16
 * @description
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //指定解码器，并且指定解析的对象
                            ch.pipeline().addLast(new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("服务器已准备完毕");
            //第定一不满口并且同步，生成了个ChanneLFuture对象
            //启动服务器（并绑定端口）
            ChannelFuture cf = bootstrap.bind(8000).sync();
            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
