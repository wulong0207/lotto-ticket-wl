package com.hhly.ticket.util;

import java.util.Date;

import org.springframework.util.Assert;

/**
 * @desc 订单生成编号
 * @author jiangwei
 * @date 2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class TicketUtil {
	
	private  static final Number TICKET =new Number(99999,5);
	
	private  static final Number ONLY =new Number(99999,5);
	//服务器ip后3位
	public static final String SERVICE_ID;
	//服务器ip最后一位
	public static final String SERVICE_ID_ONE;
	
	public static final String SERVICE_IP;
	
	static{
		//服务ip取本机ip第4段，不足3位用0填充
		String ip = IpUtil.getLocalIP();
		SERVICE_IP = ip;
		ip = ip.substring(ip.lastIndexOf(".") + 1);
		if (ip.length() == 1) {
			ip = "00" + ip;
		} else if (ip.length() == 2) {
			ip = "0" + ip;
		}
		SERVICE_ID = ip;
		SERVICE_ID_ONE = ip.substring(2);
		Assert.hasText(SERVICE_ID);
		Assert.hasText(SERVICE_ID_ONE);
	}
	/**
	 * 获取唯一订单编号
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月9日 下午3:59:35
	 * @param code 订单编号类型
	 * @param serviceId 服务器ID,多台服务器时此ID不能重复（建议2位整数）
	 * @return
	 */
	public static String getOrderNo() {
		return TICKET.getNo(SERVICE_ID);
	}
	/**
	 * 获取一个唯一序号
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月16日 上午11:50:57
	 * @return
	 */
	public static String getOnlyNo() {
		return ONLY.getNo(SERVICE_ID);
	}

	/**
	 * @desc 生成唯一数
	 * @author jiangwei
	 * @date 2017年3月9日
	 * @company 益彩网络科技公司
	 * @version 1.0
	 */
	private static class Number {
		/**
		 * 填充数
		 */
		private final String ZERO = "0";
		/**
		 * 最大递增数
		 */
		private final int MAX;
		/**
		 * 上次递增数替换时间
		 */
		private long lastTime;
		/**
		 * 递增数
		 */
		private int num = 0;
		/**
		 * 数字长度，判断是否需要填充
		 */
		private int length;
		/**
		 * 是否添加格式化日期
		 */
		private boolean addDate;
		/**
		 * 日期格式
		 */
		private String dateFormat;
		
		public Number(int max,int length){
			this(max, true,"yyMMddHHmmss",length);
		}

		public Number(int max, boolean addDate,String dateFormat,int length) {
			super();
			this.MAX = max;
			lastTime = System.currentTimeMillis() / 1000;
			this.length = length;
			this.dateFormat = dateFormat;
			this.addDate = addDate;
		}

		/**
		 * 同步生成唯一订单号
		 * @author jiangwei
		 * @Version 1.0
		 * @CreatDate 2017年3月9日 下午4:03:01
		 * @param serviceId
		 * @return
		 */
		public synchronized String getNo(String serviceId) {
			int mantissa = 0;
			long nowTime = 0;
			synchronized (this) {
				mantissa = ++num;
				nowTime = System.currentTimeMillis() / 1000;
				if (num > MAX) {
					mantissa = num = 1;
					//需要判断本次递增周期与上次是否相同，相同需要循环等待下个周期
					while (nowTime == lastTime) {
						nowTime = System.currentTimeMillis() / 1000;
					}
					lastTime = nowTime;
				}
			}
			String str = String.valueOf(mantissa);
			for (int i = str.length(); i < length; i++) {
				str = ZERO + str;
			}
			if (addDate) {
				return DateUtil.convertDateToStr(new Date(nowTime * 1000),dateFormat)
						+ serviceId + str;
			}
			return serviceId + str;
		}
	}
}
