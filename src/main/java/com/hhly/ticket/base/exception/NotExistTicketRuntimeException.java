package com.hhly.ticket.base.exception;
/**
 * @desc 不存在票异常
 * @author jiangwei
 * @date 2018年3月30日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class NotExistTicketRuntimeException extends ServiceRuntimeException {

	private static final long serialVersionUID = 2591070951658824315L;

	public NotExistTicketRuntimeException(String msg) {
		super(msg);
	}

}
