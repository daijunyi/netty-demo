package com.daijunyi.netty.netty.buff;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author djy
 * @createTime 2022/11/16 16:16
 * @description
 */
public class ByteBuffTest {

    public static void main(String[] args) {
        ///1.创建对象，该对象包含一个数组arr，是一个byte[10]
        //2.
        //在netty buffer中，不需要使用Lip进行反转
        //底层维护了readerindex和writerIndex
        ///3.通过readerindex和writerIndex和
        //capacity,部uffer.分成三个区域
        //0---readerindex已经读取的区域
        //readerindex---writerIndex 可读的区域
        //writerIndex-capacity,可写的区域
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i=0;i<10;i++){
            buffer.writeByte(i);
        }

        for (int i=0;i<buffer.capacity();i++){
            byte aByte = buffer.readByte();
            System.out.println(aByte);
        }
    }
}
