package com.hhly.ticket.persistence.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hhly.ticket.base.vo.AgainOutTicketVO;
import com.hhly.ticket.service.entity.DealerCheckBO;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.entity.TicketInfoPO;
import com.hhly.ticket.service.entity.TicketStatusBO;

/**
 * @desc 票信息数据接口
 * @author jiangwei
 * @date 2017年2月20日
 * @company 益彩网络
 * @version v1.0
 */
public interface TicketInfoDaoMapper {
	/**
	 * 查询指定状态的票信息
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月9日 下午4:55:56
	 * @param ids
	 * @param status
	 * @return
	 */
	List<Integer> listTicketStatus(@Param("ids") List<Integer> ids, @Param("status") int status);
	/**
	 * 修改票状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月19日 下午5:56:35
	 * @param orderCodes 订单号
	 * @param status 状态
	 * @param reamk 备注
	 */
	void updateTicketStatusByOrderCode(@Param("orderCodes")Collection<String> orderCodes, @Param("status") int status, @Param("remark")String reamk);

	/**
	 * 查询待分配的有效票信息
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月9日 下午5:22:41
	 * @param orders
	 *            订单号
	 * @return
	 */
	List<TicketBO> getTicket(@Param("orders") List<String> orders);

	/**
	 * 修改票信息
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月10日 上午10:16:20
	 * @param list
	 */
	void updateTicketSendDealer(@Param("tickets") List<TicketBO> list);

	/**
	 * 查询未分配的订单
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月10日 上午11:34:50
	 * @param splitTime
	 *            拆票时间小于指定时间
	 * @param endTicketTime
	 *            出票截止时间小于指定时间
	 * @return
	 */
	List<TicketBO> listNoAllocationTicket(@Param("splitTime") Date splitTime,@Param("lotteryCode") String lotteryCode,@Param("lotteryIssue") String lotteryIssue);

	/**
	 * 修改票信息为已分配
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月10日 下午3:11:57
	 * @param effectiveList
	 */
	void updateTicketAllocation(@Param("tickets") List<TicketBO> effectiveList);

	/**
	 * 查询已分配且分配送票时间小于指定时间票
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月10日 下午4:18:57
	 * @param outTime
	 * @param lotteryIssue 
	 * @param lotteryCode 
	 * @return
	 */
	List<TicketBO> listAllocationTicket(@Param("outTime") Date outTime);

	/**
	 * 修改订单出票信息
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月17日 下午6:39:17
	 * @param tickets
	 */
	void updateOutTicket(@Param("tickets") List<TicketBO> tickets);

	/**
	 * 根据批次号获取订单状态
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月17日 下午8:07:57
	 * @param betchNum
	 * @return
	 */
	List<TicketStatusBO> listOrderTicketStatus(@Param("betchNums") Collection<String> betchNum);

	/**
	 * 获取需要检票的批次号
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月18日 上午10:09:01
	 * @param lotteryCode
	 * @return
	 */
	List<DealerCheckBO> listBatchNumCheckTicket(@Param("lotteryCode") Integer lotteryCode,
			@Param("batchNums") List<String> batchNums, @Param("orderCode") String orderCode);

	/**
	 * 查询没有出票成功的票
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年6月3日 下午12:01:28
	 * @param againOutTicketVO
	 * @return
	 */
	List<TicketBO> getAgainTicket(AgainOutTicketVO againOutTicketVO);
	/**
	 * 修改票状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年6月13日 下午12:16:09
	 * @param batchNum
	 * @param status
	 * @param remark
	 */
	void updateTicketByBatchNum(@Param("batchNum")List<String> batchNum, @Param("status")int status,@Param("remark")String remark);
	/**
	 * 修改票状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月2日 下午5:55:41
	 * @param error
	 */
	void updateTicketStatusError(@Param("tickets")List<TicketBO> error);
	/**
	 * 查询票信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月8日 下午4:17:15
	 * @param orders
	 * @param batchNums
	 * @return
	 */
	List<TicketBO> listTicketInfoFail(@Param("orders")Collection<String> orders,@Param("batchNums")Collection<String> batchNums);
	/**
	 * 获取订单票状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月11日 下午5:44:10
	 * @param orderCode
	 * @return
	 */
	List<Integer> getTicketStatus(@Param("orderCode")String orderCode);
	/**
	 * 获取渠道商出票最大编号
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月12日 上午11:35:40
	 * @param channelId
	 * @param beforeNo
	 * @return
	 */
	String getChannelMaxNo(@Param("channelId")String channelId, @Param("beforeNo")String beforeNo);
	/**
	 * 获取渠道商已送票未出票的票数
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月15日 上午10:07:28
	 * @param lotteryCode
	 * @param ticketChannelId
	 * @return
	 */
	int countSendTikcet(@Param("lotteryCode")int lotteryCode, @Param("ticketChannelId")String ticketChannelId);
	/**
	 * 获取出票商送票格式
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月10日 下午4:33:32
	 * @param batchNum
	 * @param batchNumSeq
	 * @return
	 */
	TicketBO getChannelTicketContent(@Param("batchNum")String batchNum, @Param("batchNumSeq")String batchNumSeq);
	/**
	 * 查询票票是否为特定状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月14日 上午10:24:13
	 * @param ticketBO
	 * @return
	 */
	int countTicketStatus(TicketBO ticketBO);
	/**
	 * 送票失败订单
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月19日 下午4:28:20
	 * @param againOutTicketVO
	 * @return
	 */
	List<DealerCheckBO> getTicketInfoSendFail(AgainOutTicketVO againOutTicketVO);
	/**
	 * 修改票状态从送票失败为已送票
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月24日 下午4:01:04
	 * @param batchNums
	 */
	void updateSendTicketAbnormal(@Param("batchNums")List<String> batchNums);
	/**
	 * 添加切票
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年12月5日 下午5:26:15
	 * @param bo
	 */
	int addChangeTicket(TicketInfoPO po);
	/**
	 * 修改票进行切票
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年12月6日 上午11:33:10
	 * @param id
	 * @param changeId
	 * @param ticketChange
	 */
	void updateChangeTicket(@Param("id")int id, @Param("changeId")Integer changeId);
	/**
	 * 修改票
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年12月6日 下午12:21:37
	 * @param orderDetailId
	 * @param changeId
	 * @param ticketChange
	 */
	void updateTicketChannel(@Param("orderDetailId")Integer orderDetailId, @Param("changeId")Integer changeId, @Param("ticketChange")String ticketChange);
	/**
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年12月6日 下午2:43:05
	 * @param tickets
	 * @return
	 */
	List<TicketBO> findChangeTikcet(List<TicketBO> tickets);
	/**
	 * 获取订单状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年6月24日 下午6:50:55
	 * @param id
	 * @return
	 */
	String getOrderCode(@Param("id")String id);
}