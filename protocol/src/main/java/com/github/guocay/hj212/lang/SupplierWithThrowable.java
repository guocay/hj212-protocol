package com.github.guocay.hj212.lang;

/**
 * @author aCay
 */
@FunctionalInterface
public interface SupplierWithThrowable<R,T extends Throwable> {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see     Thread#run()
     */
    public abstract R get() throws T;
}
