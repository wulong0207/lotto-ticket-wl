package com.hhly.ticket.service.entity;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.hhly.ticket.service.ticket.dealer.IDealer;

/**
 * @desc 检票类
 * @author jiangwei
 * @date 2017年5月15日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class CheckBO implements Delayed {
	/**
	 * 批次号
	 */
	private String batchNum;
	/**
	 * 彩种
	 */
	private int lotteryCode;
	/**
	 * 出票商接口
	 */
	private IDealer dealer;
	/**
	 * 检票时间
	 */
	private long checkTime;

	private Date endCheckTime;
	
	private String issue;
	
	private int lotteryChildCode;

	public CheckBO(int lotteryCode,int lotteryChildCode,String issue, String batchNum, IDealer dealer, Date endCheckTime) {
		this.batchNum = batchNum;
		this.dealer = dealer;
		this.endCheckTime = endCheckTime;
		this.lotteryCode = lotteryCode;
		this.issue = issue;
		this.lotteryChildCode = lotteryChildCode;
	}

	public CheckBO(String batchNum) {
		this.batchNum = batchNum;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(checkTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	@Override
	public int compareTo(Delayed o) {
		CheckBO that = (CheckBO) o;
		if (this.checkTime > that.checkTime) {// 截止时间越大，越排在队尾.
			return 1;
		} else if (this.checkTime == that.checkTime) {
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * 重写hashCode和equals是对象放入set中不重复
	 */
	@Override
	public int hashCode() {
		return batchNum.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CheckBO)) {
			return false;
		}
		CheckBO b = (CheckBO) obj;
		if (Objects.equals(this.batchNum, b.batchNum)) {
			return true;
		}
		return false;
	}

	/**
	 * @return the batchNum
	 */
	public String getBatchNum() {
		return batchNum;
	}

	/**
	 * @param batchNum
	 *            the batchNum to set
	 */
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	/**
	 * @return the dealer
	 */
	public IDealer getDealer() {
		return dealer;
	}

	/**
	 * @param dealer
	 *            the dealer to set
	 */
	public void setDealer(IDealer dealer) {
		this.dealer = dealer;
	}

	/**
	 * @return the checkTime
	 */
	public long getCheckTime() {
		return checkTime;
	}

	/**
	 * @param checkTime
	 *            the checkTime to set
	 */
	public void setCheckTime(long checkTime) {
		this.checkTime = checkTime;
	}

	/**
	 * @return the endCheckTime
	 */
	public Date getEndCheckTime() {
		return endCheckTime;
	}

	/**
	 * @param endCheckTime
	 *            the endCheckTime to set
	 */
	public void setEndCheckTime(Date endCheckTime) {
		this.endCheckTime = endCheckTime;
	}

	/**
	 * @return the lotteryCode
	 */
	public int getLotteryCode() {
		return lotteryCode;
	}

	/**
	 * @param lotteryCode
	 *            the lotteryCode to set
	 */
	public void setLotteryCode(int lotteryCode) {
		this.lotteryCode = lotteryCode;
	}

	/**
	 * @return the issue
	 */
	public String getIssue() {
		return issue;
	}

	/**
	 * @param issue the issue to set
	 */
	public void setIssue(String issue) {
		this.issue = issue;
	}

	/**
	 * @return the lotteryChildCode
	 */
	public int getLotteryChildCode() {
		return lotteryChildCode;
	}

	/**
	 * @param lotteryChildCode the lotteryChildCode to set
	 */
	public void setLotteryChildCode(int lotteryChildCode) {
		this.lotteryChildCode = lotteryChildCode;
	}
}
