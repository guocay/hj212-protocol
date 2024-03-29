package com.github.guocay.hj212;

import com.github.guocay.hj212.core.ProtocolMapper;
import com.github.guocay.hj212.exception.ProtocolFormatException;
import com.github.guocay.hj212.model.CpData;
import com.github.guocay.hj212.model.Data;
import com.github.guocay.hj212.model.DataFlag;
import com.github.guocay.hj212.model.Pollution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 应用测试
 * @author aCay
 * @since  2023.03.27
 */
public class ApplicationTest {

    private static final String[] SAMPLE = {
            "##0317QN=20191012133710130;ST=22;CN=2011;PW=123456;MN=Y80110010000010;Flag=4;CP=&&DataTime=20191012133710;a21005-Rtd=0.00,a21005-Flag=N;a21004-Rtd=0.00,a21004-Flag=N;a21007-Rtd=0.033,a21007-Flag=N;a21006-Rtd=0.036,a21006-Flag=N;a21001-Rtd=25.40,a21001-Flag=N;a21003-Rtd=101.94,a21003-Flag=N;a21002-Rtd=49.20,a21002-Flag=N&&cc01\r\n",
            "##0275QN=20190901181526940;ST=32;CN=2011;PW=123456;MN=Y80110010000010;Flag=4;CP=&&DataTime=20190901181500;a21005-Rtd=1.760,a21005-Flag=N;a21026-Rtd=106.400,a21026-Flag=N;O3-Rtd=63.400,O3-Flag=N;a21004-Rtd=63.200,a21004-Flag=N;a34002-Rtd=9,a34002-Flag=N;a34004-Rtd=4,a34004-Flag=N&&1100\r\n",
            "##0136ST=32;CN=2011;PW=123456;MN=LD130133000015;CP=&&DataTime=20160824003817;B01-Rtd=36.91;011-Rtd=231.0,011-Flag=N;060-Rtd=1.803,060-Flag=N&&4980\r\n",
            "##0213QN=20191012133710130;ST=22;CN=2011;PW=123456;MN=Y80110010000010;Flag=4;CP=&&DataTime=20191012133710;a21-Rtd=0.00;a21-Rtd=0.00;a21007-Rtd=0.033;a21006-Rtd=0.036;a21001-Rtd=25.40;a21003-Rtd=101.94;a21002-Rtd=49.20&&f9c1\r\n",
            "##0303QN=20191012133710130;ST=22;CN=2011;PW=123456;MN=Y80110010000010;Flag=4;CP=&&DataTime=20191012133710;a2106-Rtd=0.036,a2106-Flag=N;a2107-Rtd=0.033,a2107-Flag=N;a2104-Rtd=0.00,a2104-Flag=N;a2105-Rtd=0.00,a2105-Flag=N;a2102-Rtd=49.20,a2102-Flag=N;a2103-Rtd=101.94,a2103-Flag=N;a2101-Rtd=25.40,a2101-Flag=N&&71c0\r\n",
            "##0289QN=20191012133710130;ST=22;CN=2011;PW=123456;MN=Y80110010000010;Flag=4;CP=&&DataTime=20191012133710;a214-Rtd=0.00,a214-Flag=N;a215-Rtd=0.00,a215-Flag=N;a216-Rtd=0.036,a216-Flag=N;a217-Rtd=0.033,a217-Flag=N;a211-Rtd=25.40,a211-Flag=N;a212-Rtd=49.20,a212-Flag=N;a213-Rtd=101.94,a213-Flag=N&&5780\r\n",
            "##0204QN=20191112091100598;ST=31;CN=2051;PW=123456;MN=14513567846884;PNO=0002;PNUM=0002;CP=&&DataTime=20230327090000;a01006-Avg=101.85,a01006-Min=101.84,a01006-Max=101.87,a01006-ValidNum=10,a01006-TotalNum=10&&2800\r\n"
    };

    @Test
    @DisplayName("解码")
    public void decode() throws ProtocolFormatException, IOException {
        ProtocolMapper mapper = new ProtocolMapper().enableDefaultParserFeatures().enableDefaultVerifyFeatures();

        Data data = mapper.readData(SAMPLE[5]);

        System.out.println("MN Code: " + data.getMn());
    }

    @Test
    @DisplayName("编码")
    public void encode() throws ProtocolFormatException, IOException {
        ProtocolMapper mapper = new ProtocolMapper().enableDefaultParserFeatures().enableDefaultVerifyFeatures();
        Data data = new Data();
        data.setQn("20191012133710130");
        data.setSt("22");
        data.setCn("2011");
        data.setPw("123456");
        data.setMn("Y80110010000010");
        data.setDataFlag(Arrays.asList(DataFlag.V0));
        Map<String,String> map = new HashMap<>();
        map.put("a21001","25.40");
        map.put("a21002","49.20");
        map.put("a21003","101.94");
        map.put("a21004","0.00");
        map.put("a21005","0.00");
        map.put("a21006","0.036");
        map.put("a21007","0.033");
        CpData cp = new CpData();
        cp.setDataTime("20191012133710");
        cp.setPollution(new HashMap<>());
        map.forEach((key,value)->{
            Pollution po = new Pollution();
            po.setRtd(new BigDecimal(value));
            po.setFlag("N");
            cp.getPollution().put(key,po);
        });
        data.setCp(cp);

        String str = mapper.writeDataAsString(data);

        System.out.println("HJ 212: " + str);
    }
}
