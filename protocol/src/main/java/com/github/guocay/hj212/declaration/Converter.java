package com.github.guocay.hj212.declaration;

/**
 * 转换器接口
 * @author aCay
 */
public interface Converter<SRC,TAR> {

    TAR convert(SRC src) throws Exception;
}
