package com.github.guocay.hj212.business.core.util;

/**
 * 常量池
 *
 * @author GuoKai
 * @since 2019/10/25
 */
public abstract class Constant {

	//数据有效
	public static final String EFFECTIVE = "10001000";
	//数据无效
	public static final String UN_EFFECTIVE = "10001001";

	//hj212 数据应答
	public static final String DATA_RESPONSE = "9014";

	//默认日期格式化
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public abstract static class MonitorTask {
		public static final boolean TASK_SWITCH = true;
		public static final long TASK_CYCLE = 10 * 60 * 1000L;
		public static final long CALLBACK_CYCLE = TASK_CYCLE + 60 * 1000L;
	}

	public abstract static class MonitorType {
		public static final String TYPE_LIST_AIR = "air";
		public static final String TYPE_LIST_DANGERGAS = "dangergas";
	}
}
