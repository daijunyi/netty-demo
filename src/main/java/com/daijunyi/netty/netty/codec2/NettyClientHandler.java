package com.daijunyi.netty.netty.codec2;

import com.daijunyi.netty.netty.codec.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

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
        MyDataInfo.MyMessage msg = null;
        Random random = new Random();
        int nextInt = random.nextInt(10);
        if (nextInt%2 == 0){
            msg = MyDataInfo.MyMessage
                    .newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(1).setName("我的学生").build()).build();

        }else{
            msg = MyDataInfo.MyMessage
                    .newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setAge(1).setName("工人").build()).build();
        }
        ctx.writeAndFlush(msg);
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
