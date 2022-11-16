package com.daijunyi.netty.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.EnumMap;

/**
 * @author djy
 * @createTime 2022/11/17 00:17
 * @description
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            EnumMap<IdleState, String> enumMap = new EnumMap<IdleState, String>(IdleState.class){
                {
                    put(IdleState.ALL_IDLE,"读写空闲");
                    put(IdleState.READER_IDLE,"读空闲");
                    put(IdleState.WRITER_IDLE,"写空闲");
                }
            };
            System.out.println(ctx.channel().remoteAddress()+"超时事件:"+enumMap.get(event.state()));
        }
    }
}
