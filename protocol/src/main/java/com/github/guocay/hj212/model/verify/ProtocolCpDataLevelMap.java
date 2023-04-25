package com.github.guocay.hj212.model.verify;

import com.github.guocay.hj212.core.validator.clazz.FieldC;
import com.github.guocay.hj212.core.validator.clazz.FieldN;
import com.github.guocay.hj212.core.validator.clazz.FieldValidDate;
import com.github.guocay.hj212.core.validator.field.CharArray;
import com.github.guocay.hj212.core.validator.field.NumberArray;
import com.github.guocay.hj212.core.validator.field.Date;
import com.github.guocay.hj212.model.verify.groups.ModeGroup;
import com.github.guocay.hj212.model.verify.groups.VersionGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * T212 Map
 * 解决无法对MAP进行验证定义问题
 * @author aCay
 */
@SuppressWarnings("serial")
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
public class ProtocolCpDataLevelMap
        extends ProtocolMap<String,Object> {

    public ProtocolCpDataLevelMap(Map<String, Object> m) {
        super(m);
    }

    @SuppressWarnings("unchecked")
    public Cp getCp(){
        Map<String,String> map = (Map<String, String>) this.get("CP");
        if(map == null){
            map = new HashMap<>();
        }
        return new Cp(map);
    }

    //
    @FieldValidDate(field = "SystemTime",
            value = @Date(format = "yyyyMMddHHmmss"))
    @FieldN(field = "QnRtn",
            value = @NumberArray(integer = 3))
    @FieldN(field = "ExeRtn",
            value = @NumberArray(integer = 3))
    @FieldN(field = "RtdInterval", groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 4))
    @FieldN(field = "RtdInterval", groups = VersionGroup.V2017.class,
            value = @NumberArray(integer = 4, min = 30, max = 3600))

    @FieldValidDate(field = "QN", groups = VersionGroup.V2005.class,
            value = @Date(format = "yyyyMMddHHmmssSSS"))

    @FieldN(field = "MinInterval", groups = VersionGroup.V2017.class,
            value = @NumberArray(integer = 2))
    @FieldValidDate(field = "RestartTime", groups = VersionGroup.V2017.class,
            value = @Date(format = "yyyyMMddHHmmss"))


    //污染物
    @FieldC(field = ".*-Flag", regex = true,
            value = @CharArray(len = 1))

    @FieldN(field = ".*-Rtd", regex = true, groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14, fraction = 2))
    @FieldN(field = ".*-Min", regex = true, groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14, fraction = 2))
    @FieldN(field = ".*-Avg", regex = true, groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14, fraction = 2))
    @FieldN(field = ".*-Max", regex = true, groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14, fraction = 2))
    @FieldN(field = ".*-ZsRtd", regex = true, groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14, fraction = 2))
    @FieldN(field = ".*-ZsMin", regex = true, groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14, fraction = 2))
    @FieldN(field = ".*-ZsAvg", regex = true, groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14, fraction = 2))
    @FieldN(field = ".*-ZsMax", regex = true, groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14, fraction = 2))
    @FieldN(field = ".*-Cou", regex = true, groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14, fraction = 2))
    @FieldN(field = ".*-Ala", regex = true, groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14, fraction = 2))
    @FieldN(field = ".*-UpValue", regex = true, groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14, fraction = 2))
    @FieldN(field = ".*-LowValue", regex = true, groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14, fraction = 2))

    @FieldValidDate(field = ".*-SampleTime", regex = true, groups = VersionGroup.V2017.class,
            value = @Date(format = "yyyyMMddHHmmss"))
    @FieldC(field = ".*-EFlag", regex = true, groups = VersionGroup.V2017.class,
            value = @CharArray(len = 4))

    //污染治理设施
    @FieldN(field = ".*-RS", regex = true, groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 1, min = 0, max = 1))
    @FieldN(field = ".*-RT", regex = true, groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14, fraction = 2))
    @FieldN(field = "SB.*-RS", regex = true, groups = VersionGroup.V2017.class,
            value = @NumberArray(integer = 1))
    @FieldN(field = "SB.*-RT", regex = true, groups = VersionGroup.V2017.class,
            value = @NumberArray(integer = 2, fraction = 2, min = 0, max = 24))


    //噪声
    @FieldN(field = ".*-Data", regex = true, groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14, fraction = 2))
    @FieldN(field = ".*-DayData", regex = true, groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14, fraction = 2))
    @FieldN(field = ".*-NightData", regex = true, groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14, fraction = 2))
    @FieldN(field = ".*-Data", regex = true, groups = VersionGroup.V2017.class,
            value = @NumberArray(integer = 3, fraction = 1))
    @FieldN(field = ".*-DayData", regex = true, groups = VersionGroup.V2017.class,
            value = @NumberArray(integer = 3, fraction = 1))
    @FieldN(field = ".*-NightData", regex = true, groups = VersionGroup.V2017.class,
            value = @NumberArray(integer = 3, fraction = 1))


    //
    @FieldC(field = "PolId",
            value = @CharArray(len = 6))
    @FieldValidDate(field = "BeginTime",
            value = @Date(format = "yyyyMMddHHmmss"))
    @FieldValidDate(field = "EndTime",
            value = @Date(format = "yyyyMMddHHmmss"))
    @FieldValidDate(field = "DataTime",
            value = @Date(format = "yyyyMMddHHmmss"))
    @FieldN(field = "OverTime", groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 5))
    @FieldN(field = "OverTime", groups = VersionGroup.V2017.class,
            value = @NumberArray(integer = 2))
    @FieldN(field = "ReCount",
            value = @NumberArray(integer = 2))
    @FieldN(field = "CTime", groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 2))
    @FieldN(field = "Ctime", groups = VersionGroup.V2017.class,
            value = @NumberArray(integer = 2, max = 24))

    @FieldValidDate(field = "AlarmTime", groups = VersionGroup.V2005.class,
            value = @Date(format = "yyyyMMddHHmmss"))
    @FieldN(field = "AlarmType", groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 1, min = 0, max = 1))
    @FieldN(field = "ReportTarget", groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 20))
    @FieldN(field = "ReportTime", groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 4))
    @FieldN(field = "DayStdValue", groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14))
    @FieldN(field = "NightStdValue", groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 14))
    @FieldN(field = "WarnTime", groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 5))
    @FieldN(field = "Flag", groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 3))
    @FieldN(field = "PNUM", groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 4))
    @FieldN(field = "PNO", groups = VersionGroup.V2005.class,
            value = @NumberArray(integer = 4))
    @FieldC(field = "PW", groups = VersionGroup.V2005.class,
            value = @CharArray(len = 6))

    @FieldC(field = "NewPW", groups = VersionGroup.V2017.class,
            value = @CharArray(len = 6))
    @FieldN(field = "VaseNo", groups = VersionGroup.V2017.class,
            value = @NumberArray(integer = 2))
    @FieldValidDate(field = "CstartTime", groups = VersionGroup.V2017.class,
            value = @Date(format = "HHmmss"))
    @FieldN(field = "Stime", groups = VersionGroup.V2017.class,
            value = @NumberArray(integer = 4))
    @FieldC(field = "InfoId", groups = VersionGroup.V2017.class,
            value = @CharArray(len = 6))
    @FieldC(field = ".*-SN", regex = true, groups = VersionGroup.V2017.class,
            value = @CharArray(len = 24))
    public class Cp
            extends ProtocolMap<String, String> {

        public Cp(Map<String, String> m) {
            super(m);
        }
    }

}

