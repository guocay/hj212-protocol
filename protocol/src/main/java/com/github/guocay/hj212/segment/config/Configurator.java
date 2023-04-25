package com.github.guocay.hj212.segment.config;

/**
 * @author aCay
 */
public interface Configurator<Target> {

    /**
     * 配置
     * @param target 配置目标
     */
    void config(Target target);
}
