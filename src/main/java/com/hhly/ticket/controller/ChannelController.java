package com.hhly.ticket.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.ticket.base.common.ResultBO;
import com.hhly.ticket.base.controller.BaseController;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.base.vo.AgainOutTicketVO;
import com.hhly.ticket.service.IChannelManageService;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.DealerCheckBO;
import com.hhly.ticket.service.ticket.TicketHandler;
import com.hhly.ticket.service.ticket.dealer.INotify;
import com.hhly.ticket.service.ticket.dealer.NotifyFactory;
import com.hhly.ticket.service.ticket.dealer.jimi.cathectic.JiMiDealer;
import com.hhly.ticket.service.ticket.dealer.saiwei.NotifyResult;
import com.hhly.ticket.util.FileUtil;

/**
 * @desc 渠道商回调接口
 * @author jiangwei
 * @date 2017年12月19日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/channel")
public class ChannelController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(ChannelController.class);

	@Autowired
	private TicketHandler handler;

	@Autowired
	private IOrderService orderService;

	@Autowired
	private IChannelManageService channelManageService;

	/**
	 * 清理出票商服务
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月18日 上午10:05:19
	 * @return
	 */
	@RequestMapping(value = "/restart")
	@ResponseBody
	public Object clearChannel(String lotteryCode) {
		LOGGER.info("清理出票商渠道lotteryCode:" + lotteryCode);
		handler.clearChannel(lotteryCode);
		return ResultBO.getSuccess(null);
	}

	/**
	 * 睿朗主动通知接口
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月19日 下午12:10:12
	 * @param cmd
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "/ruilang/api")
	@ResponseBody
	public Object ruilang(String cmd, String msg) {
		INotify ruiLangNotify = NotifyFactory.getNotify(TicketHandler.RUI_LANG_ID, channelManageService, orderService);
		String result = "";
		if ("1004".equals(cmd)) {
			LOGGER.info("睿朗出票商回调出票成功" + msg);
			result = ruiLangNotify.notifyOutTicket(msg);
		} else {
			result = ruiLangNotify.getSuccessReuslt();
		}
		return "cmd=" + cmd + "&msg=" + result;
	}

	/**
	 * 恒朋主动通知接口
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月14日 下午12:19:15
	 * @param transType
	 * @param transMessage
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/hengpeng/api", method = RequestMethod.POST)
	@ResponseBody
	public Object hengpeng(String transType, String transMessage, HttpServletRequest request) {
		INotify hengPengNotify = NotifyFactory.getNotify(TicketHandler.HENG_PENG_ID, channelManageService,
				orderService);
		String result = "";
		if ("104".equals(transType)) {
			LOGGER.info("恒朋出票商回调出票成功" + transMessage);
			result = "transType=504&transMessage=" + hengPengNotify.notifyOutTicket(transMessage);
		} else {
			LOGGER.info("恒朋回调：" + transType + "|" + transMessage);
			result = "transType=501&transMessage=" + hengPengNotify.getSuccessReuslt("501", "0000", "success");
		}
		return result;

	}

	/**
	 * 高德出票通知回调
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月4日 上午9:31:57
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/gaode/api", method = RequestMethod.POST)
	@ResponseBody
	public Object gaode(HttpServletRequest request) {
		INotify gaoDeNotify = NotifyFactory.getNotify(TicketHandler.GAO_DE_ID, channelManageService, orderService);
		String msg = "";
		try {
			msg = FileUtil.getString(request.getInputStream());
		} catch (IOException e) {
			LOGGER.error("高德回调处理失败", e);
			return gaoDeNotify.getSuccessReuslt("-1");
		}
		LOGGER.info("高德出票商回调出票成功" + msg);
		return gaoDeNotify.notifyOutTicket(msg);
	}

	/**
	 * 聚享出票商回调
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月19日 下午3:01:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/juxiang/api", method = RequestMethod.POST)
	@ResponseBody
	public Object juxiang(HttpServletRequest request) {
		INotify juxiangNotify = NotifyFactory.getNotify(TicketHandler.JU_XIANG_ID, channelManageService, orderService);
		String msg = "";
		try {
			msg = FileUtil.getString(request.getInputStream());
		} catch (IOException e) {
			LOGGER.error("聚享回调处理失败", e);
			return "";
		}
		LOGGER.info("聚享通知信息" + msg);
		return juxiangNotify.notifyOutTicket(msg);
	}

	/**
	 * 吉米出票商回调
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年12月13日 下午4:33:03
	 * @param cmd
	 *            回调借口
	 * @param msg
	 *            xml 消息
	 * @return
	 */
	@RequestMapping(value = "/jimi/api", method = RequestMethod.POST)
	@ResponseBody
	public Object jimi(String cmd, String msg) {
		INotify jimiNotify = NotifyFactory.getNotify(TicketHandler.JI_MI_ID, channelManageService, orderService);
		LOGGER.info("吉米通知信息：cmd:" + cmd + ";msg：" + msg);
		if ("1001".equals(cmd)) {
			return jimiNotify.notifyOutTicket(msg);
		} else {
			return jimiNotify.getSuccessReuslt(JiMiDealer.RESPONSE_SUCCESS);
		}
	}

	/**
	 * 滕顺出票回调
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年5月18日 下午4:15:45
	 * @param cmd
	 * @param msg
	 * @return
	 */
	@RequestMapping(value = "/tengshun/api")
	@ResponseBody
	public Object tengshun(String xml, String sign) {
		INotify tengshunNotify = NotifyFactory.getNotify(TicketHandler.TENG_SHUN_ID, channelManageService,
				orderService);
		LOGGER.info("滕顺通知信息：xml:" + xml + ";sign：" + sign);
		return tengshunNotify.notifyOutTicket(xml);
	}

	/**
	 * 赛维出票回调
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年5月18日 下午4:15:45
	 * @param cmd
	 * @param msg
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/saiwei/api")
	@ResponseBody
	public Object saiwei(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result = FileUtil.getString(request.getInputStream());
		INotify saiweiNotify = NotifyFactory.getNotify(TicketHandler.SAI_WEI_ID, channelManageService, orderService);
		LOGGER.info("赛维通知信息：" + result);
		String r = saiweiNotify.notifyOutTicket(result);
		NotifyResult nr = new NotifyResult();
		nr.setCode(Integer.parseInt(r));
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		return nr;
	}

	/**
	 * 纵贯出票商通知接口
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年3月20日 下午3:36:05
	 * @param transcode
	 * @param msg
	 * @param key
	 * @param partnerid
	 * @return
	 */
	@RequestMapping(value = "/zongguan/api", method = RequestMethod.POST)
	@ResponseBody
	public Object zongguan(HttpServletRequest request) {
		INotify notify = NotifyFactory.getNotify(TicketHandler.ZHONG_GUAN_ID, channelManageService, orderService);
		String result = "";
		try {
			result = FileUtil.getString(request.getInputStream());
		} catch (IOException e) {
			LOGGER.error("纵贯回调处理失败", e);
			return "";
		}
		LOGGER.info("纵贯通知信息：" + result);
		return notify.notifyOutTicket(result);
	}
	
	/***
	 * 元润德出票商通知接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/yuanrunde/api", method = RequestMethod.POST)
	@ResponseBody
	public Object yuanrunde(HttpServletRequest request,@RequestBody String apps) {
		INotify notify = NotifyFactory.getNotify(TicketHandler.YUAN_RUN_DE_ID, channelManageService, orderService);
		String result = "";
		try {
			result = URLDecoder.decode(apps,"UTF-8");
			LOGGER.info("元润德通知信息处理前：" + result);
			result = result.replace("xml=", "");
			LOGGER.info("元润德通知信息处理后：" + result);
		} catch (Exception e) {
			LOGGER.error("元润德回调处理失败:"+e.getMessage(), e);
			return "";
		}
		return notify.notifyOutTicket(result);
	}

	/**
	 * 检票查询
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月5日 下午2:38:40
	 * @param lotteryCode
	 *            彩种
	 * @param batchNum
	 *            批次号，多个“,” 隔开
	 * @param orderCode
	 * @return
	 */
	@RequestMapping(value = "/ticket/check")
	@ResponseBody
	public Object check(Integer lotteryCode, String batchNum, String orderCode) {
		if (lotteryCode == null && StringUtils.isBlank(batchNum) && StringUtils.isBlank(orderCode)) {
			return ResultBO.getFail("参数错误", null);
		}
		LOGGER.info("检票：lotteryCode:" + lotteryCode + "|batchNum:" + batchNum + "|orderCode:" + orderCode);
		List<DealerCheckBO> batchNums = orderService.listBatchNumCheckTicket(lotteryCode, batchNum, orderCode);
		orderService.channelCheck(batchNums);
		orderService.updateSynchronizedOrderStatus(orderCode);
		return ResultBO.getSuccess(null);
	}

	/**
	 * 重新出票
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年6月3日 上午11:54:52
	 * @param againOutTicketVO
	 * @return
	 */
	@RequestMapping(value = "/ticket/again")
	@ResponseBody
	public Object againOutTikcet(AgainOutTicketVO againOutTicketVO) {
		LOGGER.info("重新出票："+ againOutTicketVO.toString());
		if (StringUtils.isBlank(againOutTicketVO.getBatchNum()) && StringUtils.isBlank(againOutTicketVO.getOrderCode())
				&& StringUtils.isBlank(againOutTicketVO.getTicket())) {
			return ResultBO.getFail("参数错误", null);
		}
		if(againOutTicketVO.getType() == 1){
			if(StringUtils.isEmpty(againOutTicketVO.getChannelId())){
				return ResultBO.getFail("切换出票商必须选择切换出票商", null);	
			}
		}
		try {
			orderService.againOutTikcet(againOutTicketVO);
			return ResultBO.getSuccess(null);
		} catch (ServiceRuntimeException e) {
			return ResultBO.getFail(e.getMessage(), "");
		}
	}

	/**
	 * 查询线程当前运行情况
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月17日 下午2:13:59
	 * @return
	 */
	@RequestMapping(value = "/thread/running")
	public String run() {
		return "log/thread_running";
	}

	/**
	 * 检票查询
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月5日 下午2:38:40
	 * @param lotteryCode
	 *            彩种
	 * @param batchNum
	 *            批次号，多个“,” 隔开
	 * @param orderCode
	 * @return
	 */
	@RequestMapping(value = "/ticket/end/check")
	@ResponseBody
	public Object endCheck(Integer lotteryCode) {
		LOGGER.info("执行彩种最终检票任务：" + lotteryCode);
		if (lotteryCode == null) {
			return ResultBO.getFail("彩种错误", null);
		}
		check(lotteryCode, null, null);
		orderService.updateOrderOutFial(lotteryCode);
		return ResultBO.getSuccess(null);
	}

	/**
	 * 对未及时送票进行处理
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月1日 下午4:33:41
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/ticket/allocation")
	@ResponseBody
	public Object allocation(String type, String lotteryCode, String lotteryIssue) {
		LOGGER.info("对未及时送票进行处理 type:" + type + "|lotteryCode:" + lotteryCode + "|lotteryIssue:" + lotteryIssue);
		orderService.allocation(type, lotteryCode, lotteryIssue);
		return ResultBO.getSuccess(null);
	}

	/**
	 * 更新出票商余额
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月9日 上午9:07:53
	 * @return
	 */
	@RequestMapping(value = "/balance")
	@ResponseBody
	public Object balance() {
		LOGGER.info("更新出票商余额");
		orderService.updateBalance();
		return ResultBO.getSuccess(null);
	}

	/**
	 * 获取出票商送票信息
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月3日 下午4:27:15
	 * @return
	 */
	@RequestMapping(value = "/send/info")
	@ResponseBody
	public Object sendInfo() {
		return ResultBO.getSuccess(handler.getSendTicketInfo());
	}
}
