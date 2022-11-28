package com.daijunyi.netty.netty.protocoltcp;

/**
 * @author djy
 * @createTime 2022/11/21 16:53
 * @description
 */
public class MessageProtocol {

    private Integer length;

    private byte[] content;

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
