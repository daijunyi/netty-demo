package com.daijunyi.netty.nio;

import java.nio.IntBuffer;

/**
 * @author djy
 * @createTime 2022/11/8 17:00
 * @description
 */
public class BasicBuffer {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i=0;i<intBuffer.capacity();i++){
            intBuffer.put(i);
        }
        //读写切换
        intBuffer.flip();
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
