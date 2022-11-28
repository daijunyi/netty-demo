package com.daijunyi.netty.netty.protocoltcp;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author djy
 * @createTime 2022/11/21 12:23
 * @description
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {
        System.out.println("接收到服务端信息长度："+messageProtocol.getLength());
        System.out.println("接收到服务端信息内容："+new String(messageProtocol.getContent(), CharsetUtil.UTF_8));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        for (int i=0;i<5;i++){
            channel.writeAndFlush("我测试一下子");
        }
    }
}
