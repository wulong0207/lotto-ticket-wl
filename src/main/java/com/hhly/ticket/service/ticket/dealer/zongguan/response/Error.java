package com.hhly.ticket.service.ticket.dealer.zongguan.response;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 错误信息返回
 * @desc
 * @author jiangwei
 * @date 2018年3月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Error {
	@XStreamAsAttribute
    private String transcode;
	@XStreamAsAttribute
    private String message;

	public String getTranscode() {
		return transcode;
	}

	public void setTranscode(String transcode) {
		this.transcode = transcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    
}
