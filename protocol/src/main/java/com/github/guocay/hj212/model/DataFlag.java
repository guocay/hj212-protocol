package com.github.guocay.hj212.model;

import java.util.Collection;

/**
 * 数据段 标志
 * @author aCay
 */
public enum DataFlag {

    /**
     * 命令是否应答
     */
    A(1),
    /**
     * 是否有数据包序号
     */
    D(2),
    /**
     * 标准版本号V0（HJ 212-2017）
     */
    V0(4),
    /**
     * 标准版本号V1
     */
    V1(8),
    /**
     * 标准版本号V2
     */
    V2(16),
    /**
     * 标准版本号V3
     */
    V3(32),
    /**
     * 标准版本号V4
     */
    V4(64),
    /**
     * 标准版本号V5
     */
    V5(128);

    private int bit;

    DataFlag(){
        this.bit = (1 << ordinal());
    }

    DataFlag(int bit){
        this.bit = bit;
    }

    public int getBit() {
        return bit;
    }

    public boolean isMarked(int flags) {
        return (flags & bit) != 0;
    }

    public boolean isMarked(Collection<DataFlag> flags) {
        return flags != null && flags.contains(this);
    }

}
