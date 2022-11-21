package com.daijunyi.netty.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author djy
 * @createTime 2022/11/21 11:55
 * @description
 */
public class MyLongDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyLongDecoder:decode调用");
        if (in.readableBytes() >= 8){
            out.add(in.readLong());
        }
    }
}
