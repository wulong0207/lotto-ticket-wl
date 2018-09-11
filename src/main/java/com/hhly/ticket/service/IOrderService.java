package com.hhly.ticket.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.hhly.ticket.base.vo.AgainOutTicketVO;
import com.hhly.ticket.service.entity.DealerCheckBO;
import com.hhly.ticket.service.entity.OrderStatusBO;
import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc 订单获取接口
 * @author jiangwei
 * @date 2017年5月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface IOrderService {
	/**
	 * 获取支付成功并且待出票的订单
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月4日 下午3:58:25
	 * @return
	 */
    List<TicketBO> getTicket(List<String> orders);
    /**
     * 验证票是否合法
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年5月5日 下午12:01:01
     * @param ids
     * @return
     */
    List<Integer> validTicket(List<Integer> ids);
    /**
     * 修改订单状态
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年5月9日 上午11:33:26
     * @param orders
     * @param value
     */
	void updateOrderStatus(Set<String> orders, int status);
	/**
	 * 送票出票商后修改票信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月10日 上午10:14:08
	 * @param list
	 */
	void updateTicketSendDealer(List<TicketBO> list);
	/**
	 * 查询为已分配且超过了分配时间的票
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月10日 下午3:30:40
	 * @param outTime
	 * @return
	 */
	List<TicketBO> listAllocationTicket(Date outTime);
	/**
	 * 修改票信息标识为已分配
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月10日 下午3:10:03
	 * @param effectiveList
	 */
	void updateTicketAllocation(List<TicketBO> effectiveList);
	/**
	 * 修改订单已出票信息
	 * 修改票出票状态，如果票出票失败不会同步修改订单状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月17日 下午6:38:14
	 * @param tickets
	 * @return 批次号
	 */
	Collection<String> updateOutTicket(List<TicketBO> tickets);
	/**
	 * 修改批次号订单状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月2日 上午9:36:09
	 * @param betchNums
	 */
	void updateOrderStatusByBetch(Collection<String> betchNums);
	/**
	 * 根据出票情况修改订单状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月17日 下午8:35:18
	 * @param orders
	 */
	void updateOrderStatusOutTicket(List<OrderStatusBO> orders);
	/**
	 * 获取需要检票的批次号
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月18日 上午10:08:00
	 * @param lotteryCode
	 * @return
	 */
	List<DealerCheckBO> listBatchNumCheckTicket(Integer lotteryCode,String batchNum,String orderCode);
	/**
	 * 重新出票
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年6月3日 上午11:55:55
	 * @param againOutTicketVO
	 */
	void againOutTikcet(AgainOutTicketVO againOutTicketVO);
	/**
	 * 通过批次号修改票状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年6月13日 下午12:14:31
	 * @param batchNum
	 * @param value
	 * @param errorcode
	 */
	void updateOutTicketByBatchNum(List<String> batchNum, int status, String errorcode);
	/**
	 * （最终检票后）修改订单出票失败
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月5日 下午4:31:39
	 * @param lotteryCode
	 * @param issueCode
	 */
	void updateOrderOutFial(Integer lotteryCode);
	/**
	 * 修改票状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月19日 下午5:53:35
	 * @param errorOrders
	 * @param status
	 * @param reamk
	 */
	void updateTicketStatusByOrderCode(Collection<String> errorOrders, int status, String reamk);
	/**
	 * 对未及时送票进行处理
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月1日 下午4:42:26
	 * @param type
	 */
	void allocation(String type,String lotteryCode,String lotteryIssue);
	/**
	 * 修改票状态，出票失败
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月2日 下午5:53:24
	 * @param error
	 */
	void updateTicketStatusError(List<TicketBO> error);
	/**
	 * 更新出票商余额
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月9日 上午9:09:33
	 */
	void updateBalance();
	/**
	 * 同步订单票状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月11日 下午3:39:02
	 * @param batchNum
	 * @param orderCode
	 */
	void updateSynchronizedOrderStatus(String orderCode);
	/**
	 * 获取渠道商出票最大编号
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月12日 上午11:34:47
	 * @param channelId
	 * @param beforeNo
	 * @return
	 */
	String getChannelMaxNo(String channelId, String beforeNo);
	/**
	 * 添加出票报警
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月14日 上午10:49:05
	 * @param lotteryCode
	 * @param issue
	 * @param batchNum
	 */
	void sendOutTicketAlarm(int lotteryCode, String issue, String batchNum);
	/**
	 * 获取渠道商已送票未出票的票数
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月15日 上午10:06:31
	 * @param lotteryCode
	 * @param ticketChannelId
	 * @return
	 */
	int getSendTicket(int lotteryCode, String ticketChannelId);
	/**
	 * 获取票的投注类容
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月10日 下午4:06:38
	 * @param batchNum
	 * @param batchNumSeq
	 * @return
	 */
	TicketBO getChannelTicketContent(String batchNum, String batchNumSeq);
	/**
	 * 检票
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月19日 下午4:02:30
	 * @param batchNums
	 * @return 返回正在出票的订单
	 */
	List<String> channelCheck(List<DealerCheckBO> batchNums);
	/**
	 * 修改出票商切票
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年12月6日 上午9:57:53
	 * @param ticketBO
	 * @return
	 */
	void updateChangeChannel(TicketBO ticketBO);
	/**
	 * 同步订单状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年6月24日 下午6:49:33
	 * @param result
	 */
	void updateSynchronizedOrderStatusById(String result);
	/**
	 * 检查任务是否能执行
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年7月18日 下午4:46:48
	 * @param project 项目
	 * @param name 任务名
	 * @return
	 */
	boolean checkTask(String project, String name);
}
