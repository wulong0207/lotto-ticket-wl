package com.hhly.ticket.service.ticket.dealer.ruilang.request;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * @desc 投注订单信息
 * @author jiangwei
 * @date 2017年5月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class CathecticOrder {
	/**
	 * 彩宝用户名
	 */
	@XStreamAsAttribute
	private String username;
	/**
	 * 彩种 ID
	 */
	@XStreamAsAttribute
	private String lotoid;
	/**
	 * 期次号
	 */
	@XStreamAsAttribute
	private String issue;
	/**
	 * 地域 ID
	 */
	@XStreamAsAttribute
	private String areaid;
	/**
	 * 订单号
	 */
	@XStreamAsAttribute
	private String orderno;
	
	@XStreamAsAttribute
	private String error;

	private CathecticUserInfo userinfo;
	/**
	 * 票
	 */
	@XStreamImplicit
	private List<CathecticTicket> ticket = new ArrayList<>();
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLotoid() {
		return lotoid;
	}

	public void setLotoid(String lotoid) {
		this.lotoid = lotoid;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public CathecticUserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(CathecticUserInfo userinfo) {
		this.userinfo = userinfo;
	}

	public List<CathecticTicket> getTicket() {
		return ticket;
	}

	public void setTicket(List<CathecticTicket> ticket) {
		this.ticket = ticket;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
