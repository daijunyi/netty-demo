package com.daijunyi.netty.netty.protocoltcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author djy
 * @createTime 2022/11/21 11:57
 * @description
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {
        System.out.println("接收到客户端信息长度:"+messageProtocol.getLength());
        System.out.println("接收到客户端信息内容:"+new String(messageProtocol.getContent(), CharsetUtil.UTF_8));
        channelHandlerContext.channel().writeAndFlush("已收到客户端发来的信息");
    }
}
