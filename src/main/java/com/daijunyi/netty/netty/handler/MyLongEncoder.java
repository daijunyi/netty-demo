package com.daijunyi.netty.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author djy
 * @createTime 2022/11/21 12:21
 * @description
 */
public class MyLongEncoder extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongEncoder:encode调用");
        out.writeLong(msg);
    }
}
