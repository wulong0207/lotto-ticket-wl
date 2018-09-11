package com.hhly.ticket.service.ticket.dealer.tengshun.response;

import org.springframework.util.StringUtils;

import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("row")
public class Row implements ICheckTicket {

	@XStreamAsAttribute
	private String agent;
	@XStreamAsAttribute
	private String balance;
	@XStreamAsAttribute
	private String gid;
	@XStreamAsAttribute
	private String pid;
	@XStreamAsAttribute
	private String codes;
	@XStreamAsAttribute
	private String mulity;
	@XStreamAsAttribute
	private String money;
	@XStreamAsAttribute
	private String tid;
	@XStreamAsAttribute
	private String tdate;
	@XStreamAsAttribute
	private String memo;
	@XStreamAsAttribute
	private String state;
	@XStreamAsAttribute
	private String tcode;
	@XStreamAsAttribute
	private String tdesc;
	@XStreamAsAttribute
	private String apply;

	@Override
	public String getBatchNum() {
		return apply;
	}

	@Override
	public String getBatchNumSeq() {
		return "1";
	}

	@Override
	public String getOfficialNum() {
		return null;
	}

	@Override
	public String getThirdNum() {
		return tid;
	}

	@Override
	public String getReceiptContent() {
		return null;
	}

	@Override
	public String getOdd() {
		return memo;
	}

	@Override
	public String getCode() {
		return tcode;
	}

	@Override
	public String getMessage() {
		return tdesc;
	}

	@Override
	public boolean isOutSuccess() {
		return "0".equals(tcode) && "2".equals(state);
	}

	@Override
	public boolean isOutFial() {
		return !"0".equals(tcode) && !StringUtils.isEmpty(tcode);
	}

	@Override
	public boolean isNotExist() {
		return "3".equals(state);
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public String getMulity() {
		return mulity;
	}

	public void setMulity(String mulity) {
		this.mulity = mulity;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTdate() {
		return tdate;
	}

	public void setTdate(String tdate) {
		this.tdate = tdate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTcode() {
		return tcode;
	}

	public void setTcode(String tcode) {
		this.tcode = tcode;
	}

	public String getTdesc() {
		return tdesc;
	}

	public void setTdesc(String tdesc) {
		this.tdesc = tdesc;
	}

	public String getApply() {
		return apply;
	}

	public void setApply(String apply) {
		this.apply = apply;
	}

}
