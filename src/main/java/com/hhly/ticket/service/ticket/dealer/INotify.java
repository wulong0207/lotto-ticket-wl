package com.hhly.ticket.service.ticket.dealer;

/**
 * @desc 出票商通知接口
 * @author jiangwei
 * @date 2017年5月19日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface INotify {
	/**
	 * 处理出票订单状态通知接口
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月19日 上午11:50:45
	 * @param msg
	 * @return
	 */
	String notifyOutTicket(String msg);
	/**
	 * 返回接受成功
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月22日 下午3:02:12
	 * @return
	 */
	String getSuccessReuslt(String ... code);
}
