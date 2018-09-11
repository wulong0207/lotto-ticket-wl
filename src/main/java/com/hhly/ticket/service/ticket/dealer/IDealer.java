package com.hhly.ticket.service.ticket.dealer;

import java.util.List;

import com.hhly.ticket.service.entity.CheckBO;
import com.hhly.ticket.service.entity.DealerCheckBO;
import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc 出票商出票接口
 * @author jiangwei
 * @date 2017年5月5日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface IDealer {
	/**
	 * 送票到出票商
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月5日 上午9:51:53
	 * @param bo
	 * @return 是否送票成功
	 */
	boolean sendTicket(List<TicketBO> ticket);
    /**
     * 出票商检票
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年5月15日 下午4:16:03
     * @param batchNum
     * @param endTicketTime 截止检票时间
     * @return 返回还未出票成功的批次号
     */
	List<String> checkTicket(List<DealerCheckBO> checkBO);
	/**
	 * 添加送票成功后的票进入检票列队
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月18日 下午2:15:15
	 * @param 检票信息
	 */ 
	void addCheckBatchNum(CheckBO bo);
	/**
	 * 获取出票商余额,如果出票商不提供查询余额接口返回-1
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月8日 下午5:42:02
	 * @return
	 */
	double getDealerBalance();
	/**
	 * 添加报警信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月14日 上午10:47:57
	 * @param lotteryCode
	 * @param issue
	 * @param batchNum
	 */
	void addAlarmInfo(int lotteryCode,String issue,String batchNum);
	/**
	 * 渠道类型：0普通渠道，1积分兑换
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年3月8日 上午10:54:13
	 * @return
	 */
	int channelType();
	
	boolean isPassMode(TicketBO bo);
	
}
