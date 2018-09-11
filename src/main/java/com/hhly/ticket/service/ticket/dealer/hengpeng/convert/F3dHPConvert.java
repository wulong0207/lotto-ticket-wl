package com.hhly.ticket.service.ticket.dealer.hengpeng.convert;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
/**
 * @desc 福彩3D
 * @author jiangwei
 * @date 2017年8月15日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class F3dHPConvert extends AbstractConvert {

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return bo.getLotteryIssue();
	}

	@Override
	protected String getLotteryCode(TicketBO bo) {
		return "3d";
	}

	@Override
	protected String getPlayType(TicketBO bo) {
		if(bo.getLotteryChildCode() == 10501){
			return "201";
		}
		return "202";
	}

	@Override
	protected String getContent(String childClass, TicketBO bo) {
		if(bo.getContentType() != 1){
			throw new ServiceRuntimeException("福彩3D恒朋只能送单式票");
		}
		String content = bo.getTicketContent();
		return content.replaceAll("\\|", ",");
	}

}
