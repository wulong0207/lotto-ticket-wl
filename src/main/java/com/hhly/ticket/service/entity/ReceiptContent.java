package com.hhly.ticket.service.entity;
/**
 * @desc 出票回执内容
 * @author jiangwei
 * @date 2017年11月23日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class ReceiptContent {
	/**
	 * 回执内容
	 */
	private String receiptContent;
	/**
	 * 回执内容详情
	 */
	private String receiptContentDetail;

	public String getReceiptContent() {
		return receiptContent;
	}

	public void setReceiptContent(String receiptContent) {
		this.receiptContent = receiptContent;
	}

	public String getReceiptContentDetail() {
		return receiptContentDetail;
	}

	public void setReceiptContentDetail(String receiptContentDetail) {
		this.receiptContentDetail = receiptContentDetail;
	}
	
	
	
} 
