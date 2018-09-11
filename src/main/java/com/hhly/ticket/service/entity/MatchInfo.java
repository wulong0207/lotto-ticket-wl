package com.hhly.ticket.service.entity;
/**
 * @desc 送票场次信息
 * @author jiangwei
 * @date 2017年10月10日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class MatchInfo {
	//比赛场次
    private String number;
    //所有玩法用“/”分割
    private String play;

	public MatchInfo(String number, String play) {
		super();
		this.number = number;
		this.play = play;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPlay() {
		return play;
	}

	public void setPlay(String play) {
		this.play = play;
	}
    
    
}
