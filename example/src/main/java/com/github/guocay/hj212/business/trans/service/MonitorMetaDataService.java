package com.github.guocay.hj212.business.trans.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.guocay.hj212.business.core.util.Constant;
import com.github.guocay.hj212.business.trans.bean.DataAir;
import com.github.guocay.hj212.business.trans.bean.DataVoc;
import com.github.guocay.hj212.business.trans.bean.Device;
import com.github.guocay.hj212.business.trans.bean.MonitorMetaData;
import com.github.guocay.hj212.business.trans.bean.Regulatory;
import com.github.guocay.hj212.business.trans.mapper.DataAirMapper;
import com.github.guocay.hj212.business.trans.mapper.DataVocMapper;
import com.github.guocay.hj212.business.trans.mapper.DeviceMapper;
import com.github.guocay.hj212.business.trans.mapper.RegulatoryMapper;
import com.github.guocay.hj212.business.trans.mapper.MonitorMetaDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MonitorMetaDataService {

	private static final Logger log = LoggerFactory.getLogger(MonitorMetaDataService.class);

	private final DeviceMapper deviceMapper;

	private final RegulatoryMapper regulatoryMapper;

	private final MonitorMetaDataMapper monitorMetaDataMapper;

	private final DataAirMapper airMapper;

	private final DataVocMapper vocMapper;

	public MonitorMetaDataService(DeviceMapper deviceMapper, RegulatoryMapper regulatoryMapper,
								  MonitorMetaDataMapper monitorMetaDataMapper, DataAirMapper airMapper,
								  DataVocMapper vocMapper) {
		this.deviceMapper = deviceMapper;
		this.regulatoryMapper = regulatoryMapper;
		this.monitorMetaDataMapper = monitorMetaDataMapper;
		this.airMapper = airMapper;
		this.vocMapper = vocMapper;
	}

	public boolean trans(String parkCode) {
		LocalDateTime localDateTime = LocalDateTime.now();
		//第一步,查出所有的设备;
		deviceMapper.getDeviceList(parkCode).forEach(item -> {
			List<Regulatory> factorList = regulatoryMapper.getFactorByDeviceType(parkCode, item.getMonitorType());
			if (factorList.size() == 0) return;
			LambdaQueryWrapper<MonitorMetaData> queryWrapper = Wrappers.lambdaQuery();
			queryWrapper.eq(MonitorMetaData::getMncode, item.getMncode())
				.in(MonitorMetaData::getFactor, factorList.stream().map(Regulatory::getCode).collect(Collectors.toList()))
				.ge(MonitorMetaData::getTime, localDateTime.minus(Constant.MonitorTask.CALLBACK_CYCLE, ChronoUnit.MILLIS))
				.eq(MonitorMetaData::getDataStatus, Constant.EFFECTIVE)
				.orderByAsc(MonitorMetaData::getDataTime);
			List<MonitorMetaData> metaList = monitorMetaDataMapper.selectList(queryWrapper);
			if (metaList.size() == 0) return;
			//第二步,将数据加工成对象,并保存;
			Map<String, Map<String, String>> map = new HashMap<>();
			metaList.forEach((meta) -> {
				Map<String, String> data;
				String dateTime;
				if (ObjectUtil.isEmpty(data = map.get(dateTime = meta.getDataTime())))
					map.put(dateTime, data = new HashMap<String, String>());
				data.put(meta.getFactor(), meta.getValue());
			});
			//第三步,插入数据;
			map.forEach((key, val) -> {
				log.info(val.toString());
				LocalDateTime time = LocalDateTime.parse(key, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
				switch (item.getMonitorType()) {
					case Constant.MonitorType.TYPE_LIST_AIR:
						DataAir dataAir = new DataAir();
						dataAir.setMncode(item.getMncode().toLowerCase());
						dataAir.setMonitorTime(time).setState("1");
						airMapper.insert(setFieldValue(dataAir, val));
						break;
					case Constant.MonitorType.TYPE_LIST_DANGERGAS:
						DataVoc dataVoc = new DataVoc();
						dataVoc.setMncode(item.getMncode().toLowerCase());
						dataVoc.setMonitorTime(time).setState("1");
						vocMapper.insert(setFieldValue(dataVoc, val));
						break;
				}
			});
			//第三步,将加工过的数据的状态置为已处理;逻辑删除
			LambdaUpdateWrapper<MonitorMetaData> updateWrapper = new LambdaUpdateWrapper<>();
			updateWrapper.set(MonitorMetaData::getDataStatus, Constant.UN_EFFECTIVE)
				.in(MonitorMetaData::getId, metaList.stream().map(MonitorMetaData::getId).collect(Collectors.toList()));
			monitorMetaDataMapper.update(null, updateWrapper);
		});
		return true;
	}

	private <T> T setFieldValue(T data, Map<String, String> val) {
		val.forEach((factor, value) -> {
			try {
				ReflectUtil.setFieldValue(data, factor, value);
			} catch (IllegalArgumentException ex) {
				log.error(ex.getMessage());
			}
		});
		return data;
	}

	@Cacheable
	public List<Regulatory> getFactorByDeviceType(String parkCode, String monitorType) {
		LambdaQueryWrapper<Regulatory> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(Regulatory::getState, "1")
			.eq(Regulatory::getType, monitorType)
			.eq(Regulatory::getParkCode, parkCode);
		return regulatoryMapper.selectList(wrapper);
	}

	@Cacheable
	public List<Device> getDeviceList(String parkCode) {
		LambdaQueryWrapper<Device> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(Device::getState, "1")
			.eq(Device::getParkCode,parkCode);
		return deviceMapper.selectList(wrapper);
	}
}
