package com.hhly.ticket.service.ticket.dealer;
/**
 * @desc 出票商返回票接口
 * @author jiangwei
 * @date 2017年10月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface ICheckTicket {
	/**
	 * 获取批次号
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月17日 上午11:33:05
	 * @return
	 */
    String getBatchNum();
    /**
     * 获取批次号顺序
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年10月17日 上午11:38:16
     * @return
     */
    String getBatchNumSeq();
    /**
     * 获取官方票号
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年10月17日 上午11:33:18
     * @return
     */
    String getOfficialNum();
    /**
     * 获取第三方票号
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年10月20日 上午10:39:29
     * @return
     */
    String getThirdNum();
    /**
     * 获取回执
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年10月17日 上午11:33:46
     * @return
     */
    String getReceiptContent();
    /**
     * 获取赔率
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年10月17日 上午11:34:30
     * @return
     */
    String getOdd();
    /**
     * 获取返回编码
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年10月20日 上午10:42:41
     * @return
     */
    String getCode();
    /**
     * 获取返回提示信息
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年10月20日 上午10:44:17
     * @return
     */
    String getMessage();
    /**
     * 已出票
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年10月17日 上午11:36:35
     * @return
     */
    boolean isOutSuccess();
    /**
     * 出票失败
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年10月17日 上午11:37:20
     * @return
     */
    boolean isOutFial();
    /**
     * 是否不存在票
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年10月25日 上午9:26:48
     * @return
     */
    boolean isNotExist();
}
