package com.hhly.ticket.service.ticket.dealer.hengpeng.request;

import com.hhly.ticket.service.ticket.dealer.hengpeng.AbstractXml;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 投注
 * @author jiangwei
 * @date 2017年8月12日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("body")
public class CathecticBody extends AbstractXml{
	
	private CathecticLotteryRequest lotteryRequest;

	/**
	 * @return the lotteryRequest
	 */
	public CathecticLotteryRequest getLotteryRequest() {
		return lotteryRequest;
	}

	/**
	 * @param lotteryRequest the lotteryRequest to set
	 */
	public void setLotteryRequest(CathecticLotteryRequest lotteryRequest) {
		this.lotteryRequest = lotteryRequest;
	}
}
