package com.hhly.ticket.base.exception;
/**
 * @desc 出票商响应失败
 * @author jiangwei
 * @date 2018年6月23日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class ResponseFaillRuntimeException extends ServiceRuntimeException {
	
	private static final long serialVersionUID = -4194522187905393517L;

	public ResponseFaillRuntimeException(String msg) {
		super(msg);
	}

	public ResponseFaillRuntimeException(String code, String msg) {
		super(code, msg);
	}

}
