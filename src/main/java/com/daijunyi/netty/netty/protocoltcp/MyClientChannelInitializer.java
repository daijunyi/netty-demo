package com.daijunyi.netty.netty.protocoltcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * @author djy
 * @createTime 2022/11/21 12:21
 * @description
 */
public class MyClientChannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtocolEncoder());
        pipeline.addLast(new ProtocolDecoder());
        pipeline.addLast(new MyClientHandler());
    }
}
