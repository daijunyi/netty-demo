package com.daijunyi.netty.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author djy
 * @createTime 2022/11/21 11:57
 * @description
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("MyServerHandler:channelRead0调用");
        System.out.println("读取到客户端发送过来的信息:"+ctx.channel().remoteAddress()+":"+msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("发送数据"+122321321L);
        channel.writeAndFlush(122321321L);
    }
}
