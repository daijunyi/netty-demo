package com.daijunyi.netty.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author djy
 * @createTime 2022/11/14 14:12
 * @description
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 通道打开
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(1).setName("我是客户端").build();
        ctx.writeAndFlush(student);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("客户端读取:"+ctx);
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务端消息"+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务端地址"+ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
