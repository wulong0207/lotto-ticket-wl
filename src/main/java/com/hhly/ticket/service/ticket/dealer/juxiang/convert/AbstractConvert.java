package com.hhly.ticket.service.ticket.dealer.juxiang.convert;

import org.apache.log4j.Logger;

import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.IConvert;
/**
 * @desc 山东11选5
 * @author jiangwei
 * @date 2017年9月19日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class AbstractConvert implements IConvert{
	
	private static final Logger LOGGER = Logger.getLogger(AbstractConvert.class);
	
	@Override
	public boolean handle(TicketBO bo) {
		try {
			bo.setChannelLotteryCode(getLotteryCode(bo));
			bo.setChannelLotteryIssue(getLotteryIssye(bo));
			bo.setChannelPlayType(getPlayType(bo));
			String content = getContent(bo.getChannelPlayType(), bo);
			String [] contents;
			if(bo.getContentType() == 1){
				contents = content.split(";");
			}else{
				contents = new String[]{content};
			}
			bo.setChips(contents.length);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < contents.length; i++) {
				if(i>0){
					sb.append(";");
				}
				sb.append(i+1);
				sb.append(";");
				sb.append(bo.getChannelPlayType());
				sb.append(";");
				sb.append(bo.getMultipleNum());
				sb.append(";");
				sb.append(contents[i]);
			}
			if(bo.getLotteryCode() == 300 || bo.getLotteryCode() == 301){
				sb.append(";");
				sb.append(bo.getChannelPassMode());
			}
			bo.setChannelTicketContent(sb.toString());
		} catch (Exception e) {
			LOGGER.error("格式转换错误",e);
			return false;
		}
		return true;
		
	}

	/**
	 * 期号转换
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月16日 下午3:11:42
	 * @param bo
	 * @return
	 */
	protected  String getLotteryIssye(TicketBO bo){
		return bo.getLotteryIssue();
	};
	/**
	 * 获取彩种
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月16日 下午2:14:20
	 * @param lotteryChildCode
	 * @return
	 */
	protected abstract String getLotteryCode(TicketBO bo);
	/**
	 * 获取自子类型
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月12日 上午10:11:22
	 * @param lotteryChildCode
	 * @return
	 */
	protected abstract String getPlayType(TicketBO bo);
	/**
	 * 获取投注内容转换后格式
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月12日 上午10:25:54
	 * @param bo
	 * @return
	 */
	protected  abstract String getContent(String playType,TicketBO bo);
	
}

