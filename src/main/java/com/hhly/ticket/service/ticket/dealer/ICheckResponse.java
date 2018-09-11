package com.hhly.ticket.service.ticket.dealer;

import java.util.List;
/**
 * @desc 检票返回接口
 * @author jiangwei
 * @date 2017年10月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface ICheckResponse {
	/**
	 * 获取消息编码
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月17日 上午11:21:48
	 * @return
	 */
    String  getCode();
    /**
     * 获取消息提示
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年10月17日 上午11:21:38
     * @return
     */
    String  getMessage();
    /**
     * 获取票
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年10月17日 上午11:21:28
     * @return
     */
    List<? extends ICheckTicket> getTicket();
    /**
     * 是否成功返回
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年10月17日 上午11:22:10
     * @return
     */
    boolean isSuccess();
    /**
     * 是否存在票
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年10月25日 上午9:57:30
     * @return
     */
    boolean isExist();
}
