package com.github.guocay.hj212.core;

import com.github.guocay.hj212.exception.ProtocolFormatException;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * 验证工具类
 * @author aCay
 */
public class VerifyUtil {

    public static void verifyChar(char[] tar, char[] src, Enum<?> e) throws ProtocolFormatException {
        Objects.requireNonNull(tar);
        Objects.requireNonNull(src);
        if(!Arrays.equals(src, tar)){
            ProtocolFormatException.static_data_match(e,tar,src);
        }
    }

    public static void verifyChar(byte[] tar, char[] src, Enum<?> e) throws ProtocolFormatException {
        char[] c = new String(tar).toCharArray();
        verifyChar(src,c,e);
    }

    public static void verifyLen(int count, int length, Enum<?> e) throws ProtocolFormatException {
        if(count != length){
            ProtocolFormatException.length_not_match(e,count,length);
        }
    }

    public static void verifyLen(String str, int min, int max, Enum<?> e) throws ProtocolFormatException {
        if(str == null){
            return;
        }
        int len = str.length();

        if(len >= min  && len <= max){
        }else{
            ProtocolFormatException.length_not_range(e,len,min,max);
        }
    }

    public static void verifyLen(String str, int length, Enum<?> e) throws ProtocolFormatException {
        if(str == null){
            return;
        }

        verifyLen(str.length(),length,e);
    }

    public static void verifyRange(int src, int min, int max, Enum<?> e) throws ProtocolFormatException {
        if(src >= min  && src <= max){
        }else{
            ProtocolFormatException.length_not_range(e,src,min,max);
        }
    }

    public static String verifyRange(String str, int min, int max, Enum<?> e) throws ProtocolFormatException {
        int src = 0;
        if(str != null){
            src = str.length();
        }

        if(src >= min  && src <= max){
        }else{
            ProtocolFormatException.length_not_range(e,src,min,max);
        }
        return str;
    }

    public static void verifyCrc(char[] msg, char[] crc, Enum<?> e) throws ProtocolFormatException {
        int crc16 = ProtocolParser.crc16Checkout(msg,msg.length);
        int crcSrc = Integer.parseInt(new String(crc),16);

        if(crc16 != crcSrc){
            ProtocolFormatException.crc_verification_failed(e,msg,crc);
        }
    }

    public static void verifyHave(Map<String,?> object, Enum<?> e) throws ProtocolFormatException {
        if(!object.containsKey(e.name())){
            ProtocolFormatException.field_is_missing(e,e.name());
        }
    }

}
