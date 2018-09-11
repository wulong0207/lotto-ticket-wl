package com.hhly.ticket.service.ticket.dealer.zongguan;

import com.hhly.ticket.util.DateUtil;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @desc 消息头
 * @author jiangwei
 * @date 2018年3月13日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Head {
	@XStreamAsAttribute
	private String transcode;
	@XStreamAsAttribute
	private String partnerid;
	@XStreamAsAttribute
	private String version = "1.0";
	@XStreamAsAttribute
	private String time;

	public Head(String transcode, String partnerid) {
		super();
		this.transcode = transcode;
		this.partnerid = partnerid;
		time = DateUtil.getNow(DateUtil.DATE_FORMAT_NUM);
	}

	public String getTranscode() {
		return transcode;
	}

	public void setTranscode(String transcode) {
		this.transcode = transcode;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
