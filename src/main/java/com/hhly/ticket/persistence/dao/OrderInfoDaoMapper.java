package com.hhly.ticket.persistence.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.hhly.ticket.service.entity.OrderStatusBO;

/**
 * @author huangb
 *
 * @Date 2016年11月30日
 *
 * @Desc 订单处理数据接口
 */
public interface OrderInfoDaoMapper {
    /**
     * 修改订单状态
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年5月9日 下午5:00:26
     * @param orders
     * @param status
     * @return
     */
	int updateOrderStatus(@Param("orders")Set<String> orders,@Param("status")int status);
    /**
     * 根据出票情况修改订单状态
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年5月17日 下午8:36:52
     * @param orders
     */
	void updateOrderStatusOutTicket(@Param("orders")List<OrderStatusBO> orders);
	/**
	 * 进行最终检票后 修改订单出票状态失败
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月5日 下午4:32:45
	 * @param lotteryCode
	 * @param issueCode
	 */
	List<String> listOrderOuting(@Param("lotteryCode")Integer lotteryCode);
	/**
	 * 查询出票失败订单
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月8日 上午9:56:41
	 * @param orders
	 * @return
	 */
	List<String> listOrderOutFail(@Param("orders")List<String> orders);
	/**
	 * 同步订单状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月11日 下午5:40:17
	 * @param orderCode
	 * @return
	 */
	int getOrderStatus(String orderCode);
	/**
	 * 修改订单状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年6月19日 下午4:47:37
	 * @param orderStatusBO
	 * @return
	 */
	int updateOrderStatusOutTicketByOne(OrderStatusBO orderStatusBO);
}