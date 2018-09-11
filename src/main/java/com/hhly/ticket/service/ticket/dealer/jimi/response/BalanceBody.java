package com.hhly.ticket.service.ticket.dealer.jimi.response;
/**
 * @desc 账户余额
 * @author jiangwei
 * @date 2017年10月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class BalanceBody {
	
     private String queryTime;
     
     private double balance;
     
     private double freeze;

	public String getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getFreeze() {
		return freeze;
	}

	public void setFreeze(double freeze) {
		this.freeze = freeze;
	}
     
     
}
