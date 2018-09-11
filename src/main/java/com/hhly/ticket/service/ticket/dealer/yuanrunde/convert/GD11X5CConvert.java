package com.hhly.ticket.service.ticket.dealer.yuanrunde.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * 元润德广东11选5转换
 * @author wul687
 */
public class GD11X5CConvert extends AbstractConvert{

	@Override
	public String getPlayType(TicketBO bo) {
		int contentType = bo.getContentType();
		String lotteryCode = getLotteryCode(bo);
		switch (bo.getLotteryChildCode()) {
			case 21002:
				return lotteryCode+getRenXuanContentType(contentType)+"2";// 广东十一选五任选二
			case 21003:
				return lotteryCode+getRenXuanContentType(contentType)+"3";// 广东十一选五任选三
			case 21004:
				return lotteryCode+getRenXuanContentType(contentType)+"4";// 广东十一选五任选四
			case 21005:
				return lotteryCode+getRenXuanContentType(contentType)+"5";// 广东十一选五任选五
			case 21006:
				return lotteryCode+getRenXuanContentType(contentType)+"6";// 广东十一选五任选六
			case 21007:
				return lotteryCode+getRenXuanContentType(contentType)+"7";// 广东十一选五任选七
			case 21008:
				return lotteryCode+getRenXuanContentType(contentType)+"8";// 广东十一选五任选八  只出单式
			case 21009:
				return lotteryCode+getQianContentType(contentType)+"1";// 广东十一选五前一  只支持复式
			case 21010:
				return lotteryCode+getQianContentType(contentType)+"4";// 广东十一选五前二组选
			case 21011:
				return lotteryCode+getQianContentType(contentType)+"4";// 广东十一选五前二直选 暂不支持
			case 21012:
				return lotteryCode+getQianContentType(contentType)+"5";// 广东十一选五前三组选
			case 21013:
				return lotteryCode+getQianContentType(contentType)+"4";// 广东十一选五前三直选 暂不支持
			default:
				throw new ServiceRuntimeException("不存在子玩法");
		}
	}
	
	/**
	 * 根据前选投注类型返回第三方匹配值
	 * @param contentType
	 * @return
	 */
	public String getQianContentType(int contentType){
		switch (contentType) {
			case 1:
				return "3";
			case 2:
				return "4";	
			case 3:
				return "5";	
			default:
				throw new ServiceRuntimeException("不存在投注类型");
		}
	}
	
	/**
	 * 根据任选投注类型返回第三方匹配值
	 * @param contentType
	 * @return
	 */
	public String getRenXuanContentType(int contentType){
		switch (contentType) {
			case 1:
				return "0";
			case 2:
				return "1";	
			case 3:
				return "2";	
			default:
				throw new ServiceRuntimeException("不存在投注类型");
		}
	}

	@Override
	public String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}

	@Override
	public String getLotteryCode(TicketBO bo) {
		return "2001";
	}

	@Override
	public String getContent(TicketBO bo) {
		StringBuffer sb = new StringBuffer(bo.getTicketContent());
		sb.append("^");
		return sb.toString();
	}

}
