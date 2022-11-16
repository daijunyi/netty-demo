package com.daijunyi.netty.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

/**
 * @author djy
 * @createTime 2022/11/16 23:33
 * @description
 */
public class Client {

    private Integer port;

    private String host;

    public Client(String host, Integer port) {
        this.port = port;
        this.host = host;
    }

    public void run(){
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new StringDecoder());
                    pipeline.addLast(new StringEncoder());
                    pipeline.addLast(new GroupClientHandler());
                }
            });
            ChannelFuture cf = bootstrap.connect(host, port).sync();
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()){
                        System.out.println("连接服务器"+host+",端口："+port+"成功");
                    }else{
                        System.out.println("连接服务器"+host+",端口："+port+"失败");
                    }
                }
            });
            Channel channel = cf.channel();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                channel.writeAndFlush(Unpooled.copiedBuffer(scanner.nextLine(), CharsetUtil.UTF_8));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 8000);
        client.run();
    }
}
