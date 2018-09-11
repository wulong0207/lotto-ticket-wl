package com.hhly.ticket.service.ticket.dealer.yaosen.convert;

import java.util.Objects;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc    七星彩
 * @author  Tony Wang
 * @date    2017年11月2日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class QxcYSConvert extends AbstractConvert {
	
	
	@Override
	protected String getLotteryCode(TicketBO bo) {
		return String.valueOf(bo.getLotteryCode());
	}
	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}
	
	@Override
	protected String getPlayType(TicketBO bo) {
		/*
		 * 本站七星彩只有普通投注
		 * 七星彩过关方式
		 * 1单式
		 * 2复式
		 */
		if(Objects.equals(bo.getLotteryChildCode(), 10701)) {
			// 1：单式；2：复式；3：胆拖；4：混合；5：上传；6：和值
			switch (bo.getContentType()) {
			case 1:
				return "1";
			case 2:
				return "2";
			default:
				throw new ServiceRuntimeException("不存在的ContentType,票bo为："+bo);
			}
		} 
		throw new ServiceRuntimeException("不存在的子玩法,票bo为："+bo);
	}
	
	/**
     *  本系统    ->过关方式^投注号码^追加（大乐透玩法需加,1追加，0不追加）
     *  单式示例：7|8|9|1|2|3|5;3|5|2|7|9|1|0 -> 1^7/8/9/1/2/3/5,3/5/2/7/9/1/0
     *  复式示例：2,8|3|3|3|8|3,4|4 -> 2^2/8//3//3//3//8//3/4//4
     */
	protected String getContent(String playType, TicketBO bo) {
		String content = bo.getTicketContent();
		switch (playType) {
		case "1":
			content = content.replaceAll("\\|", "/").replaceAll(";", ",");
			break;
		case "2":
			content = content.replaceAll("\\|", "//").replaceAll(",", "/");
			break;
		default:
			throw new ServiceRuntimeException("耀森不支付的playType,票bo为："+bo);
		}
		return playType + "^" + content;
	}
}
