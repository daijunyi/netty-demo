package com.daijunyi.netty.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author djy
 * @createTime 2022/11/16 12:19
 * @description
 *
 * SimpleChannelInboundHandler 是ChannelInboundHandlerAdapter子类
 * HttpObject是客户端和服务端之间通信封装成了一个对象
 */
public class ServerHttpHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest){
            System.out.println("msg 类型"+msg.getClass());
            System.out.println("客户端地址"+ctx.channel().remoteAddress());
            HttpRequest request = (HttpRequest) msg;
            URI uri = new URI(request.getUri());
            System.out.println("请求的路径:"+uri.getPath());
            if ("/favicon.ico".equals(uri.getPath())){
                System.out.println("对请求图标不响应");
                return;
            }
            //回复消息给浏览器[http协议]
            ByteBuf content = Unpooled.copiedBuffer("hello，我是服务器", CharsetUtil.UTF_8);
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=UTF-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            response.headers().set(HttpHeaderNames.CONTENT_ENCODING,CharsetUtil.UTF_8);
            //发送
            ctx.channel().writeAndFlush(response);
        }
    }
}
