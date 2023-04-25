package com.github.guocay.hj212.model.verify;

/**
 * 通信包
 * @author aCay
 */
public enum PacketElement {
    HEADER(2),
    DATA_LEN(4),
    DATA(-0),
    DATA_CRC(4),
    FOOTER(2);

    private int len;

    PacketElement(int len){
        this.len = len;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }
}
