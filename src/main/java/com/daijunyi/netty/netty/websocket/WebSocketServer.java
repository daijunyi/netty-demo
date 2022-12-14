package com.daijunyi.netty.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author djy
 * @createTime 2022/11/17 00:40
 * @description
 */
public class WebSocketServer {
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
                            //因为基于http协议，使用http的编码和解码器
                            pipeline.addLast(new HttpServerCodec());
                            //是以块方式写，添加ChunkedwriteHandler处理器
                            pipeline.addLast(new ChunkedWriteHandler());
                            //说明
                            //1.http数据在传输过程中是分段，HttpobjectAggregator,就是可以将多个段聚合
                            //2.这就就是为什么，当浏览器发送大量数据时，就会发出多htt印请求
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            //说明
                            //1.对应vebsocket,它的数据是以帧(frame)形式传递
                            //2.可以看到NebSocketFrame下面有六个子类
                            //3.浏览器请求时ws://儿ocalhost:7000/hello表示请求uri
                            //4.WebSocketserverProtocoLHandler核心功能是将http协议升级为ws协议，保持长连接
                            //5 是通过一个状态码101来升级协议
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                            //自定义处理器，处理业务
                            pipeline.addLast(new WebSocketTextFrameHandler());
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
