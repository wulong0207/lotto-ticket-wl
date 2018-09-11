package com.hhly.ticket.service.entity;
/**
 * @desc 渠道送票信息
 * @author jiangwei
 * @date 2017年8月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class ChannelSendInfoBO {
     private int weight;
     
     private int money;
     
     private int count;
     
	public ChannelSendInfoBO(int weight, int money, int count) {
		this.weight = weight;
		this.money = money;
		this.count = count;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
     
     
}
