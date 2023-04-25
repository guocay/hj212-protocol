package com.github.guocay.hj212.stream;

import com.github.guocay.hj212.lang.RunnableWithThrowable;
import com.github.guocay.hj212.lang.SupplierWithThrowable;

import java.io.IOException;
import java.util.Optional;

/**
 * 字符读取匹配
 * @author aCay
 */
@SuppressWarnings("rawtypes")
public interface ReaderMatch<THIS extends ReaderMatch,ParentStream extends ReaderStream,T> {

    /**
     * 字符读取流
     * @return ParentStream
     */
    ParentStream done();

    /**
     * 匹配
     * @return 匹配到
     * @throws IOException
     */
    Optional<T> match() throws IOException;


    static SupplierWithThrowable<Optional<Object>,IOException> alwaysReturnPresent(){
        return () -> Optional.of(true);
    }

    static SupplierWithThrowable<Optional<Object>,IOException> alwaysReturnNotPresent(){
        return Optional::empty;
    }

    static SupplierWithThrowable<Optional<Object>,IOException> alwaysReturnPresent(RunnableWithThrowable<IOException> runnable){
        return () -> {
            runnable.run();
            return Optional.of(true);
        };
    }

    static SupplierWithThrowable<Optional<Object>,IOException> alwaysReturnNotPresent(RunnableWithThrowable<IOException> runnable){
        return () -> {
            runnable.run();
            return Optional.empty();
        };
    }
}
