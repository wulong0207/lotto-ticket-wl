package com.hhly.ticket.service.entity;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @desc 票信息
 * @author jiangwei
 * @date 2017年5月6日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class TicketBO implements Delayed{
	/**
	 * 本地票号
	 */
	private int id;
	/**
	 * 彩种
	 */
	private int lotteryCode;
	/**
	 * 彩期
	 */
	private String lotteryIssue;
	/**
	 * 子玩法id
	 */
	private int lotteryChildCode;
	/**
	 * 票金额
	 */
	private double ticketMoney;
	/**
	 * 出票渠道ID
	 */
	private String channelId;
	/**
	 * 用户id
	 */
	private int userId;
	/**
	 * 订单方案
	 */
	private String orderCode;
	/**
	 * 出票截止时间
	 */
	private Date endTicketTime;
	/**
	 * 内容类型：1：单式；2：复式；3：胆拖；4：混合；5：上传
	 */
	private int contentType;
	/**
	 * 竞彩编号:竞技彩购买的场次编号
	 */
	private String buyScreen;
	/**
	 * 批次号
	 */
	private String batchNum;
	/**
	 * 本批次号序号
	 */
	private String batchNumSeq;
	/**
	 * 票倍数
	 * 
	 */
	private Integer multipleNum;
	/**
	 * 票状态
	 */
	private Integer ticketStatus;
	/**
	 * 第三方票序号
	 * 
	 */
	private String thirdNum;
	/**
	 * 投注内容
	 */
	private String ticketContent;
	/**
	 * 出票商转换过后的内容
	 */
	private String channelTicketContent;
	/**
	 * 出票商彩期
	 */
	private String channelLotteryIssue;
	/**
	 * 出票商彩种
	 */
	private String channelLotteryCode;
	/**
	 * 出票商玩法
	 */
	private String channelPlayType;
	/**
	 * 出票商过关方式
	 */
	private String channelPassMode;
	/**
	 * 投注类型类型
	 */
	private String channelContentType;
	/**
	 * 官方编号
	 */
	private String officialNum;
	/**
	 * 出票商返回备注
	 */
	private String channelRemark;
	/**
	 * 出票商回执类容
	 */
	private String receiptContent;
	/**
	 * 回执内容解析
	 */
	private String receiptContentDetail;
	/**
	 * 大乐透追加
	 */
	private int lottoAdd;
	/**
	 * 切票记录
	 */
	private String ticketChange;
	/**
	 * 竞彩方案最近一场比赛时间
	 */
	private Date startMatchTime;
	/**
	 * 本站开始送票时间
	 */
	private Date saleTime;
	/**
	 * 该票的允许最晚能延迟到的出票时间
	 */
	private Date outTime;
	
	private String ticketRemark;
	
	private String activitySource;
	
	private Integer orderDetailId;
	
	private Integer winningStatus;
	
	private Integer changeId;
	
	private String redeemCode;
	
	private int changeType;
	//注数
	private int chips;
	//是否存在有效送票时间
	private boolean isEffectiveSendTime;
	
	private Integer beforeTicketStatus;
	
	public TicketBO() {
	}
   

	@Override
	public int compareTo(Delayed o) {
		TicketBO that = (TicketBO) o;
		if (this.outTime.getTime() > that.outTime.getTime()) {// 出票截止时间越大，越排在队尾.
			return 1;
		} else if (this.outTime.getTime() == that.outTime.getTime()) {
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * 延迟时间计算
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		//到达开售时间从队列中取出，按照outTime计算延迟打包送票时间
		return unit.convert(this.saleTime.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	/**
	 * 重写hashCode和equals是对象放入set中不重复
	 */
	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TicketBO)) {
			return false;
		}
		TicketBO b = (TicketBO) obj;
		if (this.id == b.id) {
			return true;
		}
		return false;
	}
	
	public String getChannelPlayType() {
		return channelPlayType;
	}

	public void setChannelPlayType(String channelPlayType) {
		this.channelPlayType = channelPlayType;
	}

	public String getChannelLotteryIssue() {
		return channelLotteryIssue;
	}

	public void setChannelLotteryIssue(String channelLotteryIssue) {
		this.channelLotteryIssue = channelLotteryIssue;
	}

	public String getChannelLotteryCode() {
		return channelLotteryCode;
	}

	public void setChannelLotteryCode(String channelLotteryCode) {
		this.channelLotteryCode = channelLotteryCode;
	}

	public Date getStartMatchTime() {
		return startMatchTime;
	}

	public void setStartMatchTime(Date startMatchTime) {
		this.startMatchTime = startMatchTime;
	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLotteryCode() {
		return lotteryCode;
	}

	public void setLotteryCode(int lotteryCode) {
		this.lotteryCode = lotteryCode;
	}

	public String getLotteryIssue() {
		return lotteryIssue;
	}

	public void setLotteryIssue(String lotteryIssue) {
		this.lotteryIssue = lotteryIssue;
	}

	public double getTicketMoney() {
		return ticketMoney;
	}

	public void setTicketMoney(double ticketMoney) {
		this.ticketMoney = ticketMoney;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Date getEndTicketTime() {
		return endTicketTime;
	}

	public void setEndTicketTime(Date endTicketTime) {
		this.endTicketTime = endTicketTime;
	}

	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public String getBuyScreen() {
		return buyScreen;
	}

	public void setBuyScreen(String buyScreen) {
		this.buyScreen = buyScreen;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public Integer getMultipleNum() {
		return multipleNum;
	}

	public void setMultipleNum(Integer multipleNum) {
		this.multipleNum = multipleNum;
	}

	public String getThirdNum() {
		return thirdNum;
	}

	public void setThirdNum(String thirdNum) {
		this.thirdNum = thirdNum;
	}

	public String getTicketContent() {
		return ticketContent;
	}

	public void setTicketContent(String ticketContent) {
		this.ticketContent = ticketContent;
	}

	public String getOfficialNum() {
		return officialNum;
	}

	public void setOfficialNum(String officialNum) {
		this.officialNum = officialNum;
	}

	public String getBatchNumSeq() {
		return batchNumSeq;
	}

	public void setBatchNumSeq(String batchNumSeq) {
		this.batchNumSeq = batchNumSeq;
	}

	public int getLotteryChildCode() {
		return lotteryChildCode;
	}

	public void setLotteryChildCode(int lotteryChildCode) {
		this.lotteryChildCode = lotteryChildCode;
	}

	public Integer getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(Integer ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public String getChannelRemark() {
		return channelRemark;
	}

	public void setChannelRemark(String channelRemark) {
		this.channelRemark = channelRemark;
	}

	public String getChannelTicketContent() {
		return channelTicketContent;
	}

	public void setChannelTicketContent(String channelTicketContent) {
		this.channelTicketContent = channelTicketContent;
	}

	public int getLottoAdd() {
		return lottoAdd;
	}

	public void setLottoAdd(int lottoAdd) {
		this.lottoAdd = lottoAdd;
	}
	
	/**
	 * @return the receiptContent
	 */
	public String getReceiptContent() {
		return receiptContent;
	}

	/**
	 * @param receiptContent
	 *            the receiptContent to set
	 */
	public void setReceiptContent(String receiptContent) {
		this.receiptContent = receiptContent;
	}

	/**
	 * @return the ticketRemark
	 */
	public String getTicketRemark() {
		return ticketRemark;
	}

	/**
	 * @param ticketRemark the ticketRemark to set
	 */
	public void setTicketRemark(String ticketRemark) {
		this.ticketRemark = ticketRemark;
	}

	@Override
	public String toString() {
		return "TicketBO [id=" + id + ", lotteryCode=" + lotteryCode + ", lotteryIssue=" + lotteryIssue
				+ ", lotteryChildCode=" + lotteryChildCode + ", ticketMoney=" + ticketMoney + ", channelId=" + channelId
				+ ", orderCode=" + orderCode + ", endTicketTime=" + endTicketTime + ", contentType=" + contentType
				+ ", buyScreen=" + buyScreen + ", batchNum=" + batchNum + ", batchNumSeq=" + batchNumSeq
				+ ", multipleNum=" + multipleNum + ", ticketStatus=" + ticketStatus + ", thirdNum=" + thirdNum
				+ ", ticketContent=" + ticketContent + ", channelTicketContent=" + channelTicketContent
				+ ", channelLotteryIssue=" + channelLotteryIssue + ", channelLotteryCode=" + channelLotteryCode
				+ ", channelPlayType=" + channelPlayType + ", officialNum=" + officialNum + ", channelRemark="
				+ channelRemark + ", receiptContent=" + receiptContent + ", lottoAdd=" + lottoAdd + ", startMatchTime="
				+ startMatchTime + ", saleTime=" + saleTime + ", outTime=" + outTime + ", ticketRemark=" + ticketRemark
				+ "]";
	}

	/**
	 * @return the channelPassMode
	 */
	public String getChannelPassMode() {
		return channelPassMode;
	}

	/**
	 * @param channelPassMode the channelPassMode to set
	 */
	public void setChannelPassMode(String channelPassMode) {
		this.channelPassMode = channelPassMode;
	}

	/**
	 * @return the chips
	 */
	public int getChips() {
		return chips;
	}

	/**
	 * @param chips the chips to set
	 */
	public void setChips(int chips) {
		this.chips = chips;
	}

	public boolean isEffectiveSendTime() {
		return isEffectiveSendTime;
	}

	public void setEffectiveSendTime(boolean isEffectiveSendTime) {
		this.isEffectiveSendTime = isEffectiveSendTime;
	}

	/**
	 * @return the channelContentType
	 */
	public String getChannelContentType() {
		return channelContentType;
	}

	/**
	 * @param channelContentType the channelContentType to set
	 */
	public void setChannelContentType(String channelContentType) {
		this.channelContentType = channelContentType;
	}

	/**
	 * @return the receiptContentDetail
	 */
	public String getReceiptContentDetail() {
		return receiptContentDetail;
	}

	/**
	 * @param receiptContentDetail the receiptContentDetail to set
	 */
	public void setReceiptContentDetail(String receiptContentDetail) {
		this.receiptContentDetail = receiptContentDetail;
	}

	/**
	 * @return the ticketChange
	 */
	public String getTicketChange() {
		return ticketChange;
	}

	/**
	 * @param ticketChange the ticketChange to set
	 */
	public void setTicketChange(String ticketChange) {
		this.ticketChange = ticketChange;
	}

	/**
	 * @return the activitySource
	 */
	public String getActivitySource() {
		return activitySource;
	}

	/**
	 * @param activitySource the activitySource to set
	 */
	public void setActivitySource(String activitySource) {
		this.activitySource = activitySource;
	}

	public Integer getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	
	

	public Integer getWinningStatus() {
		return winningStatus;
	}


	public void setWinningStatus(Integer winningStatus) {
		this.winningStatus = winningStatus;
	}


	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the changeType
	 */
	public int getChangeType() {
		return changeType;
	}

	/**
	 * @param changeType the changeType to set
	 */
	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}

	/**
	 * @return the changeId
	 */
	public Integer getChangeId() {
		return changeId;
	}

	/**
	 * @param changeId the changeId to set
	 */
	public void setChangeId(Integer changeId) {
		this.changeId = changeId;
	}


	/**
	 * @return the redeemCode
	 */
	public String getRedeemCode() {
		return redeemCode;
	}


	/**
	 * @param redeemCode the redeemCode to set
	 */
	public void setRedeemCode(String redeemCode) {
		this.redeemCode = redeemCode;
	}


	/**
	 * @return the beforeTicketStatus
	 */
	public Integer getBeforeTicketStatus() {
		return beforeTicketStatus;
	}


	/**
	 * @param beforeTicketStatus the beforeTicketStatus to set
	 */
	public void setBeforeTicketStatus(Integer beforeTicketStatus) {
		this.beforeTicketStatus = beforeTicketStatus;
	}
	
	
	
}
