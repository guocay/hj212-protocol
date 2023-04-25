package com.github.guocay.hj212.model.verify;

import com.github.guocay.hj212.core.validator.field.CharArray;
import com.github.guocay.hj212.core.validator.field.Date;
import com.github.guocay.hj212.core.validator.field.NumberArray;
import com.github.guocay.hj212.model.verify.groups.TypeGroup;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


/**
 * T212 Map
 * 解决无法对MAP进行验证定义问题
 * @author aCay
 */
public class ProtocolMapEntry {

    private String key;

    @NotNull(groups = {
            DataElement.Group.QN.class,
            DataElement.Group.PNUM.class,
            DataElement.Group.PNO.class,
            DataElement.Group.ST.class,
            DataElement.Group.CN.class,
            DataElement.Group.PW.class,
            DataElement.Group.MN.class,
            DataElement.Group.Flag.class
    })
    @Date(format = "yyyyMMddHHmmssSSS", groups = DataElement.Group.QN.class)
    @Max(value = 4, groups = DataElement.Group.PNUM.class)
    @Max(value = 4, groups = DataElement.Group.PNO.class)
    @Max(value = 2, groups = DataElement.Group.ST.class)
    @Max(value = 4, groups = DataElement.Group.CN.class)
    @Max(value = 6, groups = DataElement.Group.PW.class)
    @Max(value = 14, groups = DataElement.Group.MN.class)
    @Max(value = 3, groups = DataElement.Group.Flag.class)
    @Min(value = 1, groups = DataElement.Group.Flag.class)
    @Max(value = 960, groups = DataElement.Group.CP.class)

    @Date(format = "yyyyMMddHHmmss", groups = TypeGroup.YYYYMMDDhhmmss.class)
    @Date(format = "HHmmss", groups = TypeGroup.hhmmss.class)
    @NumberArray(integer = 1, groups = TypeGroup.N1.class)
    @NumberArray(integer = 2, groups = TypeGroup.N2.class)
    @NumberArray(integer = 3, groups = TypeGroup.N3.class)
    @NumberArray(integer = 4, groups = TypeGroup.N4.class)
    @NumberArray(integer = 14, groups = TypeGroup.N14.class)
    @NumberArray(integer = 2, fraction = 2, groups = TypeGroup.N2_2.class)
    @NumberArray(integer = 3, fraction = 1, groups = TypeGroup.N3_1.class)
    @CharArray(len = 1, groups = TypeGroup.C1.class)
    @CharArray(len = 4, groups = TypeGroup.C4.class)
    @CharArray(len = 6, groups = TypeGroup.C6.class)
    @CharArray(len = 24, groups = TypeGroup.C24.class)

    private String value;

    public ProtocolMapEntry(String key, String value){
        this.key = key;
        this.value = value;
    }



    public static ProtocolMapEntry of(String key, String value){
        return new ProtocolMapEntry(key,value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
