package com.github.guocay.hj212.model.verify;

import com.github.guocay.hj212.core.validator.clazz.FieldC;
import com.github.guocay.hj212.core.validator.clazz.FieldN;
import com.github.guocay.hj212.core.validator.clazz.FieldValidDate;
import com.github.guocay.hj212.core.validator.field.CharArray;
import com.github.guocay.hj212.core.validator.field.NumberArray;
import com.github.guocay.hj212.core.validator.field.Date;
import com.github.guocay.hj212.model.verify.groups.ModeGroup;
import com.github.guocay.hj212.model.verify.groups.VersionGroup;

import java.util.Map;

/**
 * T212 Map
 * 解决无法对MAP进行验证定义问题
 * @author aCay
 */
@SuppressWarnings({ "serial", "unused" })
@FieldValidDate(field = "QN",
        value = @Date(format = "yyyyMMddHHmmssSSS"))
@FieldC(field = "ST",
        value = @CharArray(len = 2))
@FieldC(field = "CN",
        value = @CharArray(len = 4))
@FieldC(field = "PW",
        value = @CharArray(len = 6))
@FieldC(field = "MN",
        value = @CharArray(len = 14))
@FieldN(field = "Flag",
        value = @NumberArray(integer = 3))
@FieldN(field = "PNUM", groups = ModeGroup.UseSubPacket.class,
        value = @NumberArray(integer = 4, optional = false))
@FieldN(field = "PNO", groups = ModeGroup.UseSubPacket.class,
        value = @NumberArray(integer = 4, optional = false))
@FieldC(field = "CP", groups = { VersionGroup.V2005.class },
        value = @CharArray(len = 960))
@FieldC(field = "CP", groups = { VersionGroup.V2017.class },
        value = @CharArray(len = 950))
public class ProtocolDataLevelMap
        extends ProtocolMap<String,String> {

    public ProtocolDataLevelMap(Map<String,String> m) {
        super(m);
    }
}
