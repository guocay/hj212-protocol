package com.github.guocay.hj212.exception;

import com.github.guocay.hj212.model.verify.PacketElement;

/**
 * 协议转换异常
 * @author aCay
 */
@SuppressWarnings("serial")
public class ProtocolFormatException extends Exception {

    private Object result;

    public ProtocolFormatException(String message) {
        super(message);
    }

    public ProtocolFormatException(String message, Object result) {
        super(message);
        this.result = result;
    }

    public ProtocolFormatException(String message, Throwable t) {
        super(message,t);
    }


    public static void separator_position(char c, Enum<?> mode) throws ProtocolFormatException {
        throw new ProtocolFormatException("Separator position is wrong: " + c + " cant in Mode: " + mode.name());
    }

    public static void static_data_match(Enum<?> flag, char[] tar, char[] src) throws ProtocolFormatException {
        throw new ProtocolFormatException("Static data core: " + flag.toString() + ": " + new String(tar) + " -> " + new String(src));
    }

    public static void length_not_match(Enum<?> flag, int tar, int src) throws ProtocolFormatException {
        throw new ProtocolFormatException("Length does not core: " + flag.toString() + ": " + tar + " -> " + src);
    }

    public static void length_not_range(Enum<?> flag, int src, int min, int max) throws ProtocolFormatException {
        throw new ProtocolFormatException("Length does not in range: " + flag.toString() + ": " + src + " -> (" + min + "," + max + ")");
    }

    public static void field_is_missing(Enum<?> flag, String field) throws ProtocolFormatException {
        throw new ProtocolFormatException("Field is missing: " + flag.toString() + ": " + field);
    }

    public static void crc_verification_failed(Enum<?> flag, char[] msg, char[] crc) throws ProtocolFormatException {
        throw new ProtocolFormatException("Crc Verification failed: " + new String(msg) + ": " + new String(crc));
    }

    public static void crc_verification_failed(Enum<?> flag, char[] msg, int crc) throws ProtocolFormatException {
        throw new ProtocolFormatException("Crc Verification failed: " + new String(msg) + ": " + Integer.toHexString(crc));
    }

    public static void segment_exception(SegmentFormatException e) throws ProtocolFormatException {
        throw new ProtocolFormatException("Segment format exception in: " + PacketElement.DATA.toString(),e);
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public ProtocolFormatException withResult(Object result) {
        this.result = result;
        return this;
    }
}
