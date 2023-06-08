package com.github.guocay.hj212.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author aCay
 */
@SuppressWarnings("rawtypes")
public class ReaderStream<ParentMatch extends ReaderMatch> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReaderStream.class);

    private PushbackReader reader;
    private int bufSize = 1024;
    private ReaderMatch childMatch;
    private ParentMatch parentMatch;

    public static ReaderStream<NoneReadMatch> of() {
        return new ReaderStream<>(null);
    }

    public static ReaderStream<NoneReadMatch> of(PushbackReader reader) {
        return new ReaderStream<>(reader);
    }

    public static ReaderStream<NoneReadMatch> of(Reader reader) {
        return new ReaderStream<>(reader,-0);
    }

    public static ReaderStream<NoneReadMatch> of(Reader reader, int pushBackCount) {
        return new ReaderStream<>(reader, pushBackCount);
    }

    public ReaderStream(PushbackReader reader) {
        use(reader);
    }

    public ReaderStream(Reader reader, int bufSize) {
        use(reader,bufSize);
    }

    public ReaderStream(PushbackReader reader, int bufSize, ParentMatch parentMatch) {
        use(reader, bufSize, parentMatch);
    }

    public ReaderStream(Reader reader, int bufSize, ParentMatch parentMatch) {
        use(reader, bufSize, parentMatch);
    }

    public ReaderStream<ParentMatch> use(PushbackReader reader){
        char[] chars = reader.getBuf();
        if(chars.length < 1){
            this.reader = new PushbackReader(reader, this.bufSize);
            return this;
        }
        this.bufSize = chars.length;
        this.reader = reader;

        return this;
    }

    public ReaderStream<ParentMatch> use(Reader reader){
        return use(reader,-0);
    }

    public ReaderStream<ParentMatch> use(Reader reader, int bufSize) {
        if(bufSize > 0){
            this.bufSize = bufSize;
        }
        this.reader = new PushbackReader(reader, this.bufSize);
        return this;
    }

    public ReaderStream<ParentMatch> use(PushbackReader reader, int bufSize, ParentMatch parentMatch) {
        if(bufSize < 1){
            this.reader = new PushbackReader(reader, this.bufSize);
            this.parentMatch = parentMatch;
            return this;
        }
        this.bufSize = bufSize;
        this.reader = reader;
        this.parentMatch = parentMatch;
        return this;
    }

    public ReaderStream<ParentMatch> use(Reader reader, int bufSize, ParentMatch parentMatch) {
        if(bufSize > 0){
            this.bufSize = bufSize;
        }
        this.reader = new PushbackReader(reader, this.bufSize);
        this.parentMatch = parentMatch;
        return this;
    }

    protected PushbackReader reader(){
        return reader;
    }

    protected int bufSize() {
        return bufSize;
    }

    /**
     * 接下来的一个字符
     * @return SingleCharMatch
     */
    public SingleCharMatch<ReaderStream<ParentMatch>> next() {
        SingleCharMatch<ReaderStream<ParentMatch>> charMatch = new SingleCharMatch<>(this);
        this.childMatch = charMatch;
        return charMatch;
    }

    /**
     * 接下来的几个字符
     * @param count 个数
     * @return MultipleCharMatch
     */
    public MultipleCharMatch<ReaderStream<ParentMatch>> next(int count) {
        MultipleCharMatch<ReaderStream<ParentMatch>> charMatch = new MultipleCharMatch<>(this,count);
        this.childMatch = charMatch;
        return charMatch;
    }

    public ParentMatch back() {
        return parentMatch;
    }

    @SuppressWarnings("unchecked")
	public Optional<Object> match() throws IOException {
        return childMatch.match();
    }

    public int read() throws IOException {
        int count = 0;
        while (!match().isPresent()){
            reader.skip(1);
            count++;
        }
        return count;
    }

    public int read(CharBuffer charBuffer) throws IOException {
        int count = 0;
        int len = charBuffer.remaining();

        int i;
        while (len > 0 && !match().isPresent()){
            i = reader.read();
            if(i == -1){
                break;
            }
            charBuffer.append((char) i);
            len--;
            count++;
        }
        return count;
    }

    public ReaderStream read(int maxCount, Consumer<CharBuffer> consumer) throws IOException {
        CharBuffer charBuffer = CharBuffer.allocate(maxCount);
        read(charBuffer);
        consumer.accept(charBuffer);
        return this;
    }

    @Override
    public String toString() {
        return (parentMatch != null ? parentMatch.toString() : "") +
                "/" + this.getClass().getSimpleName() + "(" + bufSize + ")";
    }

}
