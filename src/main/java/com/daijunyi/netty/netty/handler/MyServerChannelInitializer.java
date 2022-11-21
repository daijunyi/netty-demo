package com.daijunyi.netty.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * @author djy
 * @createTime 2022/11/21 11:54
 * @description
 */
public class MyServerChannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyLongDecoder());
        pipeline.addLast(new MyLongEncoder());
        pipeline.addLast(new MyServerHandler());
    }
}
