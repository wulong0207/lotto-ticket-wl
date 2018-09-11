package com.hhly.ticket.service.ticket.dealer.hengpeng.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @desc 彩期查询结果
 * @author jiangwei
 * @date 2017年8月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("Issue")
public class Issue {
	@XStreamAsAttribute
    private String gameName;
	@XStreamAsAttribute
    private String number;
	@XStreamAsAttribute
    private String startTime;
	@XStreamAsAttribute
    private String stopTime;
	@XStreamAsAttribute
    private String officialStartTime;
	@XStreamAsAttribute
    private String officialStopTime;
	@XStreamAsAttribute
    private String status;
	@XStreamAsAttribute
    private String bonusCode;

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public String getOfficialStartTime() {
		return officialStartTime;
	}

	public void setOfficialStartTime(String officialStartTime) {
		this.officialStartTime = officialStartTime;
	}

	public String getOfficialStopTime() {
		return officialStopTime;
	}

	public void setOfficialStopTime(String officialStopTime) {
		this.officialStopTime = officialStopTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBonusCode() {
		return bonusCode;
	}

	public void setBonusCode(String bonusCode) {
		this.bonusCode = bonusCode;
	}
    
    
}
