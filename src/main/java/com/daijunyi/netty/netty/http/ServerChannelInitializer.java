package com.daijunyi.netty.netty.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author djy
 * @createTime 2022/11/16 12:15
 * @description
 */
public class ServerChannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //加入-个netty提供jhttpservercodec codec=>[coder-decoder]
        //HttpServerCodec说明
        //l.HttpServercodec是netty是供的处理http的编码解码器
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        //添加自己的处理器
        pipeline.addLast("MyInboundHandler",new ServerHttpHandler());
    }
}
