package com.hhly.ticket.service.ticket.dealer.juxiang.response;

import org.springframework.util.StringUtils;

import com.hhly.ticket.service.ticket.dealer.ICheckTicket;

import net.sf.json.JSONObject;

public class CheckTicket implements ICheckTicket {
	
	private String orderId;
	
	private String innerId;
	
	private String tickSn;
	
	private String status;
	
	private String errCode;
	
	private String msg;
	
	private String spInfo;
	
	private String rangQiu;
	
	
	public CheckTicket(JSONObject temp){
		orderId = temp.optString("orderId","");
		innerId = temp.optString("innerId","");
		tickSn = temp.optString("tickSn", "");
		status = temp.optString("status","");
		errCode = temp.optString("errCode","");
		msg = temp.optString("msg","");
		spInfo = temp.optString("spInfo","");
		rangQiu = temp.optString("rangQiu","");
	}
	
	@Override
	public String getBatchNum() {
		return orderId;
	}

	@Override
	public String getBatchNumSeq() {
		return "1";
	}

	@Override
	public String getOfficialNum() {
		return tickSn;
	}

	@Override
	public String getThirdNum() {
		return innerId;
	}
	
	@Override
	public String getReceiptContent() {
		return "";
	}
    /*
     * @see 赔率与让球彩果用#分割
     */
	@Override
	public String getOdd() {
		if(StringUtils.isEmpty(rangQiu)){
			return spInfo;
		}
		return spInfo +"#"+ rangQiu;
	}

	@Override
	public boolean isOutSuccess() {
		return "2".equals(status);
	}

	@Override
	public boolean isOutFial() {
		return "3".equals(status) || "-1".equals(status);
	}

	@Override
	public String getCode() {
		return errCode;
	}

	@Override
	public String getMessage() {
		return msg;
	}

	@Override
	public boolean isNotExist() {
		return "4".equals(status);
	}
	

}
