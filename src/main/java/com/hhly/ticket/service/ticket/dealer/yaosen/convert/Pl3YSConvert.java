package com.hhly.ticket.service.ticket.dealer.yaosen.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc    排列3
 * @author  Tony Wang
 * @date    2017年11月2日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class Pl3YSConvert extends AbstractConvert {
	
	
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
		 * 耀森排三过关方式
		 * 11	直选单式
		 * 12	组三单式
		 * 13	组六单式
		 * 21	直选复式
		 * 22	组三复式
		 * 23	组六复式
		 * 31	直选和值
		 * 32	组选和值
		 */
		switch (bo.getLotteryChildCode()) {
		case 10401:// 排列3 直选
			// 1：单式；2：复式；3：胆拖；4：混合；5：上传；6：和值
			switch (bo.getContentType()) {
			case 1:
				return "11";
			case 2:
				return "21";
			case 6:
				return "31";
			default:
				throw new ServiceRuntimeException("不存在的ContentType,票bo为："+bo);
			}
		
		case 10402:// 排列3 组三 
			switch (bo.getContentType()) {
			case 1:
				return "12";
			case 2:
				return "22";
			// 组选和值拆票项目已拆成单式票
			default:
				throw new ServiceRuntimeException("不存在的ContentType,票bo为："+bo);
			}
		case 10403:// 排列3 组六
			switch (bo.getContentType()) {
			case 1:
				return "13";
			case 2:
				return "23";
			// 组选和值拆票项目已拆成单式票
			default:
				throw new ServiceRuntimeException("不存在的ContentType,票bo为："+bo);
			}
		default:
			throw new ServiceRuntimeException("不存在子玩法,票bo为："+bo);
		}
	}
	
	/**
     *  本系统    ->过关方式^投注号码^追加（大乐透玩法需加,1追加，0不追加）
     *  直选单式示例：1|0|0;0|0|1;0|1|0 -> 11^1/0/0,0/0/1,0/1/0
     *  组三单式示例：0,0,1;8,9,9 -> 12^0/0/1,8/9/9
     *  组六单式示例：0,6,9;0,3,9 -> 13^0/6/9,0/3/9
     *  直选复式示例：7|2|8,9 -> 21^7//2//8/9
     *  组三复式示例：4,8 -> 22^4/8
     *  组六复式示例：1,3,8,9 -> 23^1/3/8/9
     *  直选和值示例：01,04 -> 31^01/04
     */
	protected String getContent(String playType, TicketBO bo) {
		String content = bo.getTicketContent();
		// 组选和值lotto-split已拆成单式票，所以lotto-ticket不会有组选和值票
		switch (playType) {
		case "11":
			content = content.replaceAll("\\|", "/").replaceAll(";", ",");
			break;
		case "21":
			content = content.replaceAll("\\|", "//").replaceAll(",", "/");
			break;
		case "31":
			content = content.replaceAll(",", "/");
			break;
		case "12":
			content = content.replaceAll(",", "/").replaceAll(";", ",");
			break;
		case "22":
			content = content.replaceAll(",", "/");
			break;
		case "13":
			content = content.replaceAll(",", "/").replaceAll(";", ",");
			break;
		case "23":
			content = content.replaceAll(",", "/");
			break;
		default:
			throw new ServiceRuntimeException("耀森不支付的playType,票bo为："+bo);
		}
		return playType + "^" + content;
	}
}
