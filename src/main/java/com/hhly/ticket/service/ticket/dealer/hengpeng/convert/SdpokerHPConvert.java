package com.hhly.ticket.service.ticket.dealer.hengpeng.convert;

import java.util.HashMap;
import java.util.Map;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 恒朋山东快乐扑克3
 * @author jiangwei
 * @date 2017年9月6日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class SdpokerHPConvert extends AbstractConvert {
	/**
	 * 玩法
	 */
	private static Map<String, String> TYPE;
	
	static{
		TYPE = new HashMap<>();
		//key :子玩法_选好方式（选好内容）
		TYPE.put("22501_1", "101");
		TYPE.put("22501_2", "101");
		TYPE.put("22502_1", "102");
		TYPE.put("22502_2", "103");
		TYPE.put("22502_3", "104");
		TYPE.put("22503_1", "105");
		TYPE.put("22503_2", "106");
		TYPE.put("22503_3", "107");
		TYPE.put("22504_1", "108");
		TYPE.put("22504_2", "109");
		TYPE.put("22504_3", "110");
		TYPE.put("22505_1", "111");
		TYPE.put("22505_2", "112");
		TYPE.put("22505_3", "113");
		TYPE.put("22506_1", "114");
		TYPE.put("22506_2", "115");
		TYPE.put("22506_3", "116");
		TYPE.put("22507_1", "117");//同花
		TYPE.put("22507_AT", "122");
		TYPE.put("22508_1", "119");//顺子
		TYPE.put("22508_X,Y,Z", "122");
		TYPE.put("22509_1", "121");//对子
		TYPE.put("22509_X,X", "122");
		TYPE.put("22510_1", "120");//豹子
		TYPE.put("22510_Y,Y,Y", "122");
		TYPE.put("22511_1", "118");//同花顺
		TYPE.put("22511_AS", "122");
		
		//任选
		TYPE.put("A", "01");
		TYPE.put("2", "02");
		TYPE.put("3", "03");
		TYPE.put("4", "04");
		TYPE.put("5", "05");
		TYPE.put("6", "06");
		TYPE.put("7", "07");
		TYPE.put("8", "08");
		TYPE.put("9", "09");
		TYPE.put("1", "1");
		TYPE.put("0", "0");
		TYPE.put("J", "11");
		TYPE.put("Q", "12");
		TYPE.put("K", "13");
		TYPE.put(",", ",");
		TYPE.put(";", ";");
		TYPE.put("#", "#");
        //同花		
		TYPE.put("1T", "01");
		TYPE.put("2T", "02");
		TYPE.put("3T", "03");
		TYPE.put("4T", "04");
		TYPE.put("AT", "07");
		//同花顺
		TYPE.put("1S", "01");
		TYPE.put("2S", "02");
		TYPE.put("3S", "03");
		TYPE.put("4S", "04");
		TYPE.put("AS", "08");
		//顺子
		TYPE.put("A,2,3", "01");
		TYPE.put("2,3,4", "02");
		TYPE.put("3,4,5", "03");
		TYPE.put("4,5,6", "04");
		TYPE.put("5,6,7", "05");
		TYPE.put("6,7,8", "06");
		TYPE.put("7,8,9", "07");
		TYPE.put("8,9,10", "08");
		TYPE.put("9,10,J", "09");
		TYPE.put("10,J,Q", "10");
		TYPE.put("J,Q,K", "11");
		TYPE.put("Q,K,A", "12");
		TYPE.put("X,Y,Z", "09");
		//豹子
		TYPE.put("A,A,A", "01");
		TYPE.put("2,2,2", "02");
		TYPE.put("3,3,3", "03");
		TYPE.put("4,4,4", "04");
		TYPE.put("5,5,5", "05");
		TYPE.put("6,6,6", "06");
		TYPE.put("7,7,7", "07");
		TYPE.put("8,8,8", "08");
		TYPE.put("9,9,9", "09");
		TYPE.put("10,10,10", "10");
		TYPE.put("J,J,J", "11");
		TYPE.put("Q,Q,Q", "12");
		TYPE.put("K,K,K", "13");
		TYPE.put("Y,Y,Y", "10");
		//对子
		TYPE.put("A,A,*", "01");
		TYPE.put("2,2,*", "02");
		TYPE.put("3,3,*", "03");
		TYPE.put("4,4,*", "04");
		TYPE.put("5,5,*", "05");
		TYPE.put("6,6,*", "06");
		TYPE.put("7,7,*", "07");
		TYPE.put("8,8,*", "08");
		TYPE.put("9,9,*", "09");
		TYPE.put("10,10,*", "10");
		TYPE.put("J,J,*", "11");
		TYPE.put("Q,Q,*", "12");
		TYPE.put("K,K,*", "13");
		TYPE.put("X,X", "11");
		
	}

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "HPoker";
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		String key = bo.getLotteryChildCode() + "_"+ bo.getTicketContent();
		String type = TYPE.get(key);
		if(type == null){
			key = bo.getLotteryChildCode() + "_"+ bo.getContentType();
			type = TYPE.get(key);
		}
		if(type == null){
			throw new ServiceRuntimeException("山东快乐扑克不存在玩法");	
		}
		return type;
	}

	@Override
	protected String getContent(String playType, TicketBO bo) {
		int type = Integer.parseInt(playType);
		StringBuffer sb = new StringBuffer();
		if(type <= 116){
			String content = bo.getTicketContent();
			if(type == 101){
				//任选1只有复试投注，没有单式投注
				content = content.replaceAll(";", ",");
			}
			char[] codes = content.toCharArray();
			for (char b : codes) {
				sb.append(TYPE.get(String.valueOf(b)));
			}
		}else{
			String [] codes = bo.getTicketContent().split(";");
			sb.append(TYPE.get(codes[0]));
			for (int i = 1; i < codes.length; i++) {
				sb.append(",").append(TYPE.get(codes[i]));
			}
		}
		return sb.toString();
	}

}
