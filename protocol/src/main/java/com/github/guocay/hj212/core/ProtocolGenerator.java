package com.github.guocay.hj212.core;

import com.github.guocay.hj212.exception.ProtocolFormatException;
import com.github.guocay.hj212.model.verify.PacketElement;
import com.github.guocay.hj212.segment.config.Configurator;
import com.github.guocay.hj212.segment.config.Configured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;

/**
 * 协议生成者
 * @author aCay
 */
public class ProtocolGenerator implements Configured<ProtocolGenerator>, Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolGenerator.class);

    public static char[] HEADER = new char[]{ '#','#' };
    public static char[] FOOTER = new char[]{ '\r', '\n' };

    protected Writer writer;
    //not use
    @SuppressWarnings("unused")
	private int generatorFeature;

    public ProtocolGenerator(Writer writer){
        this.writer = writer;
    }

    /**
     * 设置生成特性
     * @param generatorFeature 生成特性
     */
    public void setGeneratorFeature(int generatorFeature) {
        this.generatorFeature = generatorFeature;
    }

    /**
     * 写入 包头
     * @see PacketElement#HEADER
     * @return count always 2
     * @throws IOException
     */
    public int writeHeader() throws IOException {
        writer.write(HEADER);
        return 2;
    }

    /**
     * 写入 数据段长度
     * @see PacketElement#DATA_LEN
     * @param len chars
     * @return length always 4
     * @throws IOException
     * @throws ProtocolFormatException
     */
    public int writeDataLen(char[] len) throws IOException, ProtocolFormatException {
        VerifyUtil.verifyLen(len.length, 4, PacketElement.DATA_LEN);
        writer.write(len);
        return 4;
    }

    /**
     * 写入 4字节Integer
     * @param i integer
     * @return length always 4
     * @throws IOException
     */
    public int writeHexInt32(int i) throws IOException {
        char[] intChars = Integer.toHexString(i).toCharArray();
        writer.write(intChars);
        return intChars.length;
    }

    /**
     * 读取 数据段
     * @see PacketElement#DATA
     * @param data data chars
     * @return data length
     * @throws IOException
     */
    public int writeData(char[] data) throws IOException {
        writer.write(data);
        return data.length;
    }

    /**
     * 写入 DATA_CRC 校验
     * @see PacketElement#DATA_CRC
     * @param crc crc chars
     * @return length always 4
     * @throws ProtocolFormatException
     * @throws IOException
     */
    public int writeCrc(char[] crc) throws IOException, ProtocolFormatException {
        VerifyUtil.verifyLen(crc.length, 4, PacketElement.DATA_LEN);
        writer.write(crc);
        return crc.length;
    }

    /**
     * 写入 数据段长度+CRC校验
     * @see PacketElement#DATA_LEN
     * @param data data chars
     * @return data length with 8 chars(4 chars for data_len and 4 chars for data_crc)
     * @throws ProtocolFormatException
     * @throws IOException
     */
    public int writeDataAndLenAndCrc(char[] data) throws IOException, ProtocolFormatException {
        int dataLen = data.length;
        char[] len = String.format("%04d", dataLen).toCharArray();
        writer.write(len);
        VerifyUtil.verifyLen(len.length, 4, PacketElement.DATA_CRC);

        writer.write(data);

        int crc = ProtocolParser.crc16Checkout(data,dataLen);
        int crcLen = writeHexInt32(crc);
        VerifyUtil.verifyLen(crcLen, 4, PacketElement.DATA_CRC);

        return len.length + data.length + crcLen;
    }

    /**
     * 写入 包尾
     * @see PacketElement#FOOTER
     * @return length always 2
     * @throws IOException
     */
    public int writeFooter() throws IOException {
        writer.write(FOOTER);
        return 2;
    }


    @Override
    public void configured(Configurator<ProtocolGenerator> configurator) {
        configurator.config(this);
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
