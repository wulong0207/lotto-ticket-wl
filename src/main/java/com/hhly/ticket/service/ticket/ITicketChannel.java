package com.hhly.ticket.service.ticket;

import java.util.List;

import com.hhly.ticket.service.entity.DealerCheckBO;
import com.hhly.ticket.service.entity.TicketBO;

/**
 * @desc 渠道商出票接口
 * @author jiangwei
 * @date 2017年5月4日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface ITicketChannel{
	/**
	 * 添加送票信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月5日 上午9:26:39
	 * @param bo
	 * @return
	 */
	boolean addTicket(TicketBO bo);
	/**
	 * 获取停售玩法
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月6日 下午4:00:42
	 * @return
	 */
	List<String>  getStopLottery();
	/**
	 * 检测（当天）送票时间是否合法
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月8日 上午10:47:25
	 * @param outTime 送票时间（毫秒时间戳）
	 * @param interval 送票间隔(秒)
	 * @return 一个最近的合法出票时间（如果outime 合法返回outTime）,如果不存在返回-1
	 */
	long checkOutTime(long outTime,int interval);
	/**
	 * 获取渠道ID
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月9日 上午9:11:42
	 * @return
	 */
	String getChannelId();
	/**
	 * 删除出票渠道信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月15日 下午12:12:23
	 * @param bo
	 */
	List<TicketBO> clearChannelGet();
	/**
	 * 根据批次号进行检票超作
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月15日 下午4:14:23
	 * @param checkList 检票信息
	 * @param immediately true（为true 返回正在出票中的票） 立刻检票 false 加入列队阻塞检票
	 */
	List<String> checkTicket(List<DealerCheckBO> checkList,boolean immediately);
	/**
	 * 获取该渠道权重
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年6月6日 下午5:03:25
	 * @return
	 */
	int getWeight();
	/**
	 * 获取1000内指定随机数，用于计算出票时间
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月5日 上午9:47:24
	 * @return
	 */
	int getRandom();
	/**
	 * 获取送票间隔
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月5日 上午10:54:03
	 * @return
	 */
	int [] getSendLaw();
	/**
	 * 获取渠道送票票数
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月4日 上午10:34:48
	 * @return
	 */
	int getTicketCount();
	/**
	 * 获取渠道送票金额
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月4日 上午10:34:51
	 * @return
	 */
	int getTicketMoney();
	/**
	 * 添加渠道送票金额
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月4日 上午10:34:57
	 * @param num
	 */
	void addTicketMoney(int num);
	/**
	 * 添加渠道送票票数
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月4日 上午10:35:00
	 * @param num
	 */
	void addTicketCount(int num);
	/**
	 * 比较当前渠道和参数渠道按照权重分配时的差额
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月4日 上午10:37:37
	 * @param channel 比较渠道
	 * @param source 比较数据源获取策略
	 * @return
	 */
	int  getDifference(ITicketChannel channel,ISource source);
	/**
	 * 获取渠道商余额
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月9日 上午8:53:27
	 * @return
	 */
	double getChannelBalance();
	/**
	 * 获取渠道商截止送票时间配置(毫秒)
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年11月8日 上午11:16:06
	 * @return
	 */
	int getDealerEndTime();
	/**
	 * 是否是积分兑换码渠道
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年3月8日 上午11:00:53
	 * @return
	 */
	boolean isIntegral();
	/**
	 * 是否包含票最大，最小金额
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年7月1日 下午6:44:59
	 * @param min
	 * @param max
	 * @return
	 */
	boolean isContain(double min,double max);
	/**
	 * 验证
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年7月5日 下午6:11:19
	 * @param ticketContent
	 * @return
	 */
	boolean isPassMode(TicketBO bo);
}
