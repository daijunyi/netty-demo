package com.daijunyi.netty.netty.chat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author djy
 * @createTime 2022/11/16 23:12
 * @description
 */
public class GroupInHandler extends ChannelInboundHandlerAdapter {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.writeAndFlush(Unpooled.copiedBuffer(new StringBuffer(channel.remoteAddress().toString()).append(":").append("上线").toString(),
                CharsetUtil.UTF_8));
        channels.add(channel);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"：激活");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"：下线");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channels.writeAndFlush(Unpooled.copiedBuffer(new StringBuffer(ctx.channel().remoteAddress().toString()).append(":").append("下线").toString()
                , CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        String transMsg = new StringBuffer(channel.remoteAddress().toString()).append(":").append(msg).toString();
        channels.forEach(ch ->{
            if (ch != channel){
                ch.writeAndFlush(Unpooled.copiedBuffer(transMsg, CharsetUtil.UTF_8));
            }
        });
    }
}
