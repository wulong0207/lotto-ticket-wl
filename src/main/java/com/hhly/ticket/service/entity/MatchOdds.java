package com.hhly.ticket.service.entity;

/**
 * @desc 出票商返回赔率信息
 * @author jiangwei
 * @date 2017年11月22日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class MatchOdds {
    //赔率
	private String odd;
	//竞猜目标
	private String target;

	public String getOdd() {
		return odd;
	}

	public void setOdd(String odd) {
		this.odd = odd;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
