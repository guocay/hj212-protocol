package com.github.guocay.hj212.segment.config;

/**
 * @author aCay
 */
public interface Feature {

    boolean enabledByDefault();

    int getMask();

    boolean enabledIn(int flags);


    static <F extends Enum<F> & Feature> int collectFeatureDefaults(Class<F> enumClass) {
        int flags = 0;
        for (F value : enumClass.getEnumConstants()) {
            if (value.enabledByDefault()) {
                flags |= value.getMask();
            }
        }
        return flags;
    }
}
