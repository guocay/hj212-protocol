package com.github.guocay.hj212.core.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.guocay.hj212.declaration.Converter;
import com.github.guocay.hj212.model.CpData;
import com.github.guocay.hj212.model.Data;
import com.github.guocay.hj212.model.DataFlag;
import com.github.guocay.hj212.model.Device;
import com.github.guocay.hj212.model.LiveSide;
import com.github.guocay.hj212.model.Pollution;
import com.github.guocay.hj212.model.verify.ProtocolMap;
import com.github.guocay.hj212.segment.config.Configurator;
import com.github.guocay.hj212.segment.config.Configured;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 转换器
 * 将 {@link Data} 转为 {@link ProtocolMap}
 * @author aCay
 */
@SuppressWarnings({"unused","rawtypes","unchecked"})
public class DataReverseConverter
        implements Converter<Data, ProtocolMap<String,Object>>,
        Configured<DataReverseConverter> {

    private ObjectMapper objectMapper;

    /**
     * 转换 现场端
     * @param map
     * @return
     */
    private Map<String,Map<String,String>> convertLiveSide(Map<String, LiveSide> map){
        if(map == null){
            return null;
        }
        return map.entrySet().stream()
                .map(kv -> {
                    Map<String,String> value = objectMapper.convertValue(kv.getValue(),Map.class);
                    return new AbstractMap.SimpleEntry<>(kv.getKey(),value);
                })
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue
                ));
    }

    /**
     * 转换 设备
     * @param map
     * @return
     */
	private Map<String,Map<String,String>> convertDevice(Map<String, Device> map){
        if(map == null){
            return null;
        }
        return map.entrySet().stream()
                .map(kv -> {
                    String key = "SB" + kv.getKey();
                    Map<String,String> value = objectMapper.convertValue(kv.getValue(),Map.class);
                    return new AbstractMap.SimpleEntry<>(key,value);
                })
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue
                ));
    }

    /**
     * 转换 污染因子
     * @param map
     * @return
     */
	private Map<String,Map<String,String>> convertPollution(Map<String, Pollution> map){
        if(map == null){
            return null;
        }
        return map.entrySet().stream()
                .map(kv -> {
                    Map<String,String> value = objectMapper.convertValue(kv.getValue(),Map.class);
                    return new AbstractMap.SimpleEntry<>(kv.getKey(),value);
                })
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue,
                        (u,v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); },
                        LinkedHashMap::new
                ));
    }

    /**
     * 转换 数据区
     * @param cp
     * @return
     */
	private ProtocolMap<String,Object> convertDataLevel(CpData cp){
        Map map = objectMapper
                .convertValue(cp,Map.class);

        if(cp.getDataFlag() != null &&
                !cp.getDataFlag().isEmpty()){
            String flag = convertDataFlag(cp.getDataFlag());
            map.put(Data.FLAG,flag);
        }

        if(cp.getDevice() != null &&
                !cp.getDevice().isEmpty()){
            map.remove(CpData.DEVICE);
            Map<String,Map<String,String>> device = convertDevice(cp.getDevice());
            map.putAll(device);
        }

        if(cp.getLiveSide() != null &&
                !cp.getLiveSide().isEmpty()){
            map.remove(CpData.LIVE_SIDE);
            Map<String,Map<String,String>> liveSide = convertLiveSide(cp.getLiveSide());
            map.putAll(liveSide);
        }

        if(cp.getPollution() != null &&
                !cp.getPollution().isEmpty()){
            map.remove(CpData.POLLUTION);
            Map<String,Map<String,String>> pollution = convertPollution(cp.getPollution());
            map.putAll(pollution);
        }

        return ProtocolMap.createCpDataLevel(map);
    }

    /**
     * 转换 数据段标记
     * @param flag
     * @return
     */
    private String convertDataFlag(List<DataFlag> flag){
        int i = 0;
        if(flag == null){
            return "";
        }
        for (DataFlag dataFlag : flag) {
            i += dataFlag.getBit();
        }
        return Integer.toString(i);
    }

    /**
     * 转换 数据段
     * @param data
     * @return
     */
    private ProtocolMap<String,Object> convertDataLevel(Data data){
        Map map = objectMapper
                .convertValue(data,Map.class);
        if(data.getDataFlag() != null &&
                !data.getDataFlag().isEmpty()){
            String flag = convertDataFlag(data.getDataFlag());
            map.put(Data.FLAG,flag);
        }

        if(data.getCp() != null){
            Map<String,Object> cpMap = convertDataLevel(data.getCp());
            map.put(Data.CP,cpMap);
        }

        return ProtocolMap.createCpDataLevel(map);
    }

    @Override
    public ProtocolMap<String,Object> convert(Data data) {
        return convertDataLevel(data);
    }

    @Override
    public void configured(Configurator<DataReverseConverter> by) {
        by.config(this);
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
