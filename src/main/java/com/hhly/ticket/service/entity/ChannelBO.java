package com.hhly.ticket.service.entity;

/**
 * @desc 渠道信息
 * @author jiangwei
 * @date 2017年5月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class ChannelBO {
	/**
	 * 主键ID
	 */
	private int id;
	/**
	 * 出票商名称
	 */
	private String drawerName;
	/**
	 * 渠道商ID
	 */
	private String ticketChannelId;
	/**
	 * 彩种
	 */
	private int lotteryCode;
	/**
	 * 批次号前缀
	 */
	private String preBatch;
	/**
	 * 送票权重;数字越大，权重越大 
	 */
	private int sendWeight;
	/**
	 * 每次发送票的数量
	 */
	private int sendEachBatch;
	/**
	 * 开启线程数
	 */
	private int threadCount;
	/**
	 * 0：不主动查票；1：主动查票
	 */
	private int searchAuto;
	/**
	 * 查票标识
	 */
	private String searchIdent;
	/**
	 * 查票最大票数
	 */
	private int searchMaxTicket;
	/**
	 * 截止送票后，下此开始售票的时间间隔
	 */
	private String endSendSpace;
	/**
	 * 允许送票时间时间段；
	 */
	private String allowSendTime;
	/**
	 * 停售玩法
	 */
	private String stopLottery;
	/**
	 * 出票商截止送票时间
	 */
	private int dealerEndTime;
	/**
	 * 送票地址
	 */
	private String sendUrl;
	/**
	 * 查询地址
	 */
	private String searchUrl;
	/**
	 * 账号名
	 */
	private String  drawerAccount;
	/**
	 * 密码
	 */
	private String accountPassword;
	
	private Integer startMoney;
	
	private Integer endMoeny;
	
	private String authCode;
	
	
	public ChannelBO(){
		
	}
	
	public String getSearchUrl() {
		return searchUrl;
	}
	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}
	public String getDrawerAccount() {
		return drawerAccount;
	}
	public void setDrawerAccount(String drawerAccount) {
		this.drawerAccount = drawerAccount;
	}
	public String getAccountPassword() {
		return accountPassword;
	}
	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}
	public String getSendUrl() {
		return sendUrl;
	}
	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPreBatch() {
		return preBatch;
	}
	public void setPreBatch(String preBatch) {
		this.preBatch = preBatch;
	}
	public int getSendWeight() {
		return sendWeight;
	}
	public void setSendWeight(int sendWeight) {
		this.sendWeight = sendWeight;
	}
	public int getSendEachBatch() {
		return sendEachBatch;
	}
	public void setSendEachBatch(int sendEachBatch) {
		this.sendEachBatch = sendEachBatch;
	}
	public int getThreadCount() {
		return threadCount;
	}
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	public int getSearchAuto() {
		return searchAuto;
	}
	public void setSearchAuto(int searchAuto) {
		this.searchAuto = searchAuto;
	}
	
	public String getSearchIdent() {
		return searchIdent;
	}

	public void setSearchIdent(String searchIdent) {
		this.searchIdent = searchIdent;
	}

	public int getSearchMaxTicket() {
		return searchMaxTicket;
	}
	public void setSearchMaxTicket(int searchMaxTicket) {
		this.searchMaxTicket = searchMaxTicket;
	}
	
	public String getEndSendSpace() {
		return endSendSpace;
	}

	public void setEndSendSpace(String endSendSpace) {
		this.endSendSpace = endSendSpace;
	}

	public String getAllowSendTime() {
		return allowSendTime;
	}
	public void setAllowSendTime(String allowSendTime) {
		this.allowSendTime = allowSendTime;
	}
	public String getStopLottery() {
		return stopLottery;
	}
	public void setStopLottery(String stopLottery) {
		this.stopLottery = stopLottery;
	}
	public int getLotteryCode() {
		return lotteryCode;
	}
	public void setLotteryCode(int lotteryCode) {
		this.lotteryCode = lotteryCode;
	}
	public String getTicketChannelId() {
		return ticketChannelId;
	}
	public void setTicketChannelId(String ticketChannelId) {
		this.ticketChannelId = ticketChannelId;
	}
	
	public String getDrawerName() {
		return drawerName;
	}
	
	public void setDrawerName(String drawerName) {
		this.drawerName = drawerName;
	}

	/**
	 * @return the dealerEndTime
	 */
	public int getDealerEndTime() {
		return dealerEndTime;
	}

	/**
	 * @param dealerEndTime the dealerEndTime to set
	 */
	public void setDealerEndTime(int dealerEndTime) {
		this.dealerEndTime = dealerEndTime;
	}

	public Integer getStartMoney() {
		return startMoney;
	}

	public void setStartMoney(Integer startMoney) {
		this.startMoney = startMoney;
	}

	public Integer getEndMoeny() {
		return endMoeny;
	}

	public void setEndMoeny(Integer endMoeny) {
		this.endMoeny = endMoeny;
	}

	/**
	 * @return the authCode
	 */
	public String getAuthCode() {
		return authCode;
	}

	/**
	 * @param authCode the authCode to set
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	
	
	
}
