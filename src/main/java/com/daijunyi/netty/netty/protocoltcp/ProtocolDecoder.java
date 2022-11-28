package com.daijunyi.netty.netty.protocoltcp;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author djy
 * @createTime 2022/11/21 16:59
 * @description
 */
public class ProtocolDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setContent(bytes);
        messageProtocol.setLength(length);
        list.add(messageProtocol);
    }
}
