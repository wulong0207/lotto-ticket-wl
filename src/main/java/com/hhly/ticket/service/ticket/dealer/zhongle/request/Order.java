package com.hhly.ticket.service.ticket.dealer.zhongle.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("Order")
public class Order {

	@XStreamAsAttribute
	private String Id;

	private String LotID;

	private String LotIssue;

	private String ChipMoney;

	private String ChipMulti;

	private String OneMoney;

	private String ChipCode;

	
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getLotID() {
		return LotID;
	}

	public void setLotID(String lotID) {
		LotID = lotID;
	}

	public String getLotIssue() {
		return LotIssue;
	}

	public void setLotIssue(String lotIssue) {
		LotIssue = lotIssue;
	}

	public String getChipMoney() {
		return ChipMoney;
	}

	public void setChipMoney(String chipMoney) {
		ChipMoney = chipMoney;
	}

	public String getChipMulti() {
		return ChipMulti;
	}

	public void setChipMulti(String chipMulti) {
		ChipMulti = chipMulti;
	}

	public String getOneMoney() {
		return OneMoney;
	}

	public void setOneMoney(String oneMoney) {
		OneMoney = oneMoney;
	}

	public String getChipCode() {
		return ChipCode;
	}

	public void setChipCode(String chipCode) {
		ChipCode = chipCode;
	}

}
