package com.daijunyi.netty.netty.buff;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author djy
 * @createTime 2022/11/16 16:38
 * @description
 */
public class ByteBuffTest1 {

    public static void main(String[] args) {
        ByteBuf context = Unpooled.copiedBuffer("hello world!", CharsetUtil.UTF_8);
        //获取出所有数据
        byte[] array = context.array();
        System.out.println(new String(array,0,context.readableBytes(),CharsetUtil.UTF_8));
        //获取出当前可读取的角标
        System.out.println(context.readerIndex());//0
        //当前写入的角标
        System.out.println(context.writerIndex());//12
        //总容量
        System.out.println(context.capacity());//64
        //可读取的内容数量
        System.out.println(context.readableBytes());//12
        //读取第一个
        System.out.println(context.getByte(0));//104
        //读一个后移读取位置
        System.out.println(context.readByte());//104
        System.out.println(context.readerIndex());//1
        System.out.println(context.readableBytes());//11
    }
}
