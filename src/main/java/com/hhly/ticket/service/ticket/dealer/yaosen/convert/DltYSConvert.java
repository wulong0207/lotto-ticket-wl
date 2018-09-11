package com.hhly.ticket.service.ticket.dealer.yaosen.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc    大乐透
 * @author  Tony Wang
 * @date    2017年11月2日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DltYSConvert extends AbstractConvert {
	
	
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
		 * 耀森大乐透过关方式
		 * 1	单式
		 * 2	复式
		 * 3	胆拖
		 */
		switch (bo.getLotteryChildCode()) {
		case 10201:// 普通投注
			// 1：单式；2：复式；3：胆拖；4：混合；5：上传；6：和值
			switch (bo.getContentType()) {
			case 1:
				return "1";
			case 2:
				return "2";
			default:
				throw new ServiceRuntimeException("不存在的ContentType,票bo为："+bo);
			}
		case 10202:// 胆拖投注 
			switch (bo.getContentType()) {
			case 3:
				return "3";
			default:
				throw new ServiceRuntimeException("不存在的ContentType,票bo为："+bo);
			}
		default:
			throw new ServiceRuntimeException("不存在子玩法,票bo为："+bo);
		}
	}
	
	 /**
     *  本系统    ->过关方式^投注号码^追加（大乐透玩法需加,1追加，0不追加）
     *  单：05,10,19,30,34+03,07;xxxx -> 1^05/10/19/30/34//03/07^0,xxxx(多注用,分隔)
     *  复：05,10,19,30,34,35+03,07 -> 2^05/10/19/30/34/35//03/07^0
     *  胆拖：04,09,13,21#03,05,15,22,25,30,32,35+07,11 -> 3^04/09/13/21//03/05/15/22/25/30/32/35,07/11^0
     */
	@Override
	protected String getContent(String playType, TicketBO bo) {
		// 数据库表中的大乐透追号:0否;1是
		String content = bo.getTicketContent();
		// 1：单式；2：复式；3：胆拖；
		switch (playType) {
		case "1":
			content = content.replaceAll(",", "/").replaceAll("\\+", "//").replaceAll(";", ",");
			break;
		case "2":
			content = content.replaceAll(",", "/").replaceAll("\\+", "//");
			break;
		case "3":
			// 耀森的格式，后区没胆码时则后区没有"//"
			content = content.replaceAll(",", "/").replaceAll("#", "//").replaceAll("\\+", ",");
			break;
		default:
			throw new ServiceRuntimeException("不存在内容类型,票bo为："+bo);
		}
		return bo.getContentType() + "^" + content + "^" + bo.getLottoAdd();
	}
}
