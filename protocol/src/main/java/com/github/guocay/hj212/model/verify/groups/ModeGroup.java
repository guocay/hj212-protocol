package com.github.guocay.hj212.model.verify.groups;

/**
 * 模式 验证组
 * @author aCay
 */
public interface ModeGroup {
    /**
     * 严格模式
     */
    @Deprecated
    interface Strict{}

    /**
     * 分包模式
     */
    interface UseSubPacket{}
}
