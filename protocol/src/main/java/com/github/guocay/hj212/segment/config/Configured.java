package com.github.guocay.hj212.segment.config;

/**
 * @author aCay
 */
public interface Configured<Target> {

    /**
     * 被配置
     * @param by 目标配置器
     */
    void configured(Configurator<Target> by);
}
