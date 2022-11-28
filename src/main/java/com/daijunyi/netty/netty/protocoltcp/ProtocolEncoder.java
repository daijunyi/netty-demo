package com.daijunyi.netty.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

/**
 * @author djy
 * @createTime 2022/11/21 16:56
 * @description
 */
public class ProtocolEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf byteBuf) throws Exception {
        byte[] bytes = s.getBytes(Charset.forName("utf-8"));
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
