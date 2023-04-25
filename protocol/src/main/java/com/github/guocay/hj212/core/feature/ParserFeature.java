package com.github.guocay.hj212.core.feature;


import com.github.guocay.hj212.segment.config.Feature;

/**
 * 解析特性
 * @author aCay
 */
public enum ParserFeature implements Feature {

    /**
     * 头常量
     */
    HEADER_CONSTANT(true),

    /**
     * 尾常量
     */
    FOOTER_CONSTANT(false);


    private final boolean _defaultState;
    private final int _mask;

    ParserFeature(boolean defaultState) {
        _defaultState = defaultState;
        _mask = (1 << ordinal());
    }

    @Override
    public boolean enabledByDefault() { return _defaultState; }

    @Override
    public int getMask() { return _mask; }

    @Override
    public boolean enabledIn(int flags) { return (flags & _mask) != 0; }

}
