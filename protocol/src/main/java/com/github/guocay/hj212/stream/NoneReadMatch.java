package com.github.guocay.hj212.stream;

import java.io.IOException;
import java.util.Optional;

/**
 *
 * @author aCay
 */
@SuppressWarnings("rawtypes")
public class NoneReadMatch implements ReaderMatch {
    @Override
    public Optional match() throws IOException {
        return Optional.empty();
    }

    @Override
    public ReaderStream done() {
        return null;
    }
}
