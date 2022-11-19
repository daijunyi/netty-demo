package com.daijunyi.netty.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author djy
 * @createTime 2022/11/14 13:44
 * @description
 * 说明
 * 1.我们自定义一个Handler需要继续netty规定好的某个HandlerAdapter(规范)
 * 2.这时我们自定义一个Handler,才能称为一个handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * //读取数据实际（这里我门可以读取客户端发送的消息）
     * 1,ChanneLHandlerContext ctx:上下文对象，含有管道pipeline,通道channeL,地址
     * 2.0 bject msg:就是客户端发送的数据默认object
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        StudentPOJO.Student student = (StudentPOJO.Student) msg;
        System.out.println("接收到信息:"+student.getId()+"，姓名："+student.getName());
    }

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端~",CharsetUtil.UTF_8));
    }

    /**
     * 处理异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
