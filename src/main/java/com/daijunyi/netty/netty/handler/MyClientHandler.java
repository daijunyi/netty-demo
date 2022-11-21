package com.daijunyi.netty.netty.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author djy
 * @createTime 2022/11/21 12:23
 * @description
 */
public class MyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接受到服务端发送过来的信息:"+msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler:channelActive调用");
        Channel channel = ctx.channel();
        System.out.println("发送数据"+123456L);
        channel.writeAndFlush(123456L);
    }
}
