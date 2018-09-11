package com.hhly.ticket.service.ticket.dealer.ruilang.convert.ssc;
/**
 * @desc 11选5 玩法
 * @author jiangwei
 * @date 2017年7月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface IPlay {
	/**
	 * 单式
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月11日 上午9:56:18
	 * @return
	 */
    String simple(String content);
    /**
     * 复试
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年7月11日 上午9:56:27
     * @return
     */
    String complex(String content);
    /**
     * 和值
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年7月11日 上午9:56:38
     * @return
     */
    String sum(String content);
}
