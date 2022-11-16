package com.daijunyi.netty.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author djy
 * @createTime 2022/11/17 00:11
 * @description
 */
public class HeartBeatTest {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            ////加入一个netty提供IdleStateHandler
                            //说明
                            //1.IdleStateHandler是netty提供的处理空闲状态的处理器
                            //2.Long readerIdleTime：表示多长时间没有读，就会发送一个心跳检测包检测是否连接
                            //3.Long writerIdleTime：表示多长时间没有写，就会发送一个心跳检测包检测是否连接
                            //4.Long allIdleTime：表示多长时间没有读写，就会发送一个心跳检测包检测是否连接
                            //5.文档说明
                            //triggers an {@link IdlestateEvent}when a {olink Channel}has not
                            //performed
                            //read,write,or both operation for a while.
                            //米
                            //6.当IdlestateEvent触发后，就会传递给管道的下一handler去处理 通过调用（触发）下一个handler的userEventTiggered,
                            // 在该方法中去处理2 IdlestateEvent(读空闲，写空闲，读写空闲
                            pipeline.addLast(new IdleStateHandler(3,5,7, TimeUnit.SECONDS));
                            pipeline.addLast(new HeartBeatHandler());
                        }
                    });
            ChannelFuture cf = bootstrap.bind(8000).sync();
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
