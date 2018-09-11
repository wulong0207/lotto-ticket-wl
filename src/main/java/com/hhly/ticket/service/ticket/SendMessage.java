package com.hhly.ticket.service.ticket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.hhly.ticket.base.common.LotteryEnum.Lottery;
import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.rabbitmq.produce.MQProducer;
import com.hhly.ticket.util.DateUtil;

import net.sf.json.JSONObject;

/**
 * @desc 发送消息
 * @author jiangwei
 * @date 2017年6月6日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class SendMessage {
	private  static final String TICKET_EXCHANGE = "ticket";
	//添加报警信息
	private static  final String ALARM_INFO="alarm_info";
	//抄单队列列名
	public static final String QUEUE_NAME_FOR_ORDER_COPY = "copy_order_queue";
	//发送活动通知
	private static final String ACTIVITY_CHANNEL_QUEUE = "activity_channel_queue";
	//出票中
	private static final int OUTING = 5;
	//出票失败
	private static final int OUT_FAIL = 6;
	//出票成功（等待开奖）
	private static final int OUT_SUCCESS = 8;
	@Autowired
	private MQProducer producer;
	/**
	 * 出票中
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年6月6日 下午2:16:50
	 * @param orders
	 */
	public void sendOuting(Collection<String> orders){
		send(orders,OUTING,"ticket.out.now");	
	}
	/**
	 * 出票失败
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年6月6日 下午2:16:50
	 * @param orders
	 */
	public void sendOutFail(Collection<String> orders){
		send(orders, OUT_FAIL,"ticket.out.fail");	
	}
	/**
	 * 出票成功
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年6月6日 下午2:16:50
	 * @param orders
	 */
	public void sendOutSuccess(Collection<String> orders){
		send(orders, OUT_SUCCESS,"ticket.out.success");
		sendCopyOrder(orders);
		sendActivity(orders, 7);
	}
	/**
	 * 出票商余额不足
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月9日 上午9:26:46
	 * @param drawerName
	 * @param num
	 */
	public void sendBalance(String drawerName, int num) {
		sendAlarmInfo(1, 4, 2, String.format("%s剩余金额不足%s", drawerName,num),"lotto-ticket");
	}
	/**
	 * 离出票截止5分钟未出票
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月14日 上午10:49:52
	 * @param lotteryCode
	 * @param issue
	 * @param batchNum
	 */
	public void sendOutTicketAlarm(int lotteryCode, String issue, String batchNum) {
		String lotteryName = Lottery.getLottery(lotteryCode).getDesc();
		sendAlarmInfo(1, 8, 2, String.format("%s%s期离截止5分钟未出票(竞彩出票不及时)，涉及批次:%s", lotteryName,issue,batchNum),"lotto-ticket");
	}
	/**
	 * 送票失败票
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月8日 下午4:47:58
	 * @param fial
	 */
	public void sendTicketFail(List<TicketBO> fial) {
		Map<String, List<TicketBO>> map = new HashMap<>();
		for (TicketBO ticketBO : fial) {
			String key = ticketBO.getLotteryCode()+ticketBO.getLotteryIssue()+ticketBO.getTicketStatus();
			List<TicketBO> list = map.get(key);
			if(list == null){
				list = new ArrayList<>();
				map.put(key, list);
			}
			list.add(ticketBO);
		}
		for (List<TicketBO> list : map.values()) {
			TicketBO first = list.get(0);
			Integer status = first.getTicketStatus();
			if(status != null){
				StringBuilder sb = new StringBuilder();
				Set<String> remarks = new HashSet<>();
				for (TicketBO ticketBO : list) {
					if(sb.length() >0){
						sb.append(",");	
					}
					if(StringUtils.isEmpty(ticketBO.getOrderCode()) 
							|| (status == -1 && !StringUtils.isEmpty(ticketBO.getBatchNum()))
							){
						//送票失败记录批次号
						sb.append(ticketBO.getBatchNum());
					}else{
						sb.append(ticketBO.getOrderCode());	
					}
					remarks.add(ticketBO.getTicketRemark());
				}
				//-2出票失败;-1送票失败
				if(status == -1){
					sendTicketFail(first.getLotteryCode(), first.getLotteryIssue(), sb.toString(),remarks.toString());
				}else if(status == -2){
					sendTicketOutFail(first.getLotteryCode(), first.getLotteryIssue(), sb.toString(),remarks.toString());
				}
			}
		}
	}
	/**
	 * 送票失败
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月8日 下午2:38:40
	 * @param lotteryCode
	 * @param lotteryIssue
	 * @param order
	 * @param reamk
	 */
	private void sendTicketFail(int lotteryCode,String lotteryIssue,String order,String remark){
		String lotteryName = Lottery.getLottery(lotteryCode).getDesc();
		String message = String.format("%s%s送票失败,涉及%s",lotteryName,lotteryIssue ,order);
		sendAlarmInfo(1, 17, 1, message,remark+"lotto-ticket");
	}
	/**
	 * 出票失败
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月8日 下午2:59:29
	 * @param lotteryCode
	 * @param lotteryIssue
	 * @param order
	 * @param reamk
	 */
	private void sendTicketOutFail(int lotteryCode,String lotteryIssue,String order,String remark){
		String message = "";
		if(lotteryCode == 0){
			message = String.format("出票失败,涉及批次号%s",order);
		}else{
			String lotteryName = Lottery.getLottery(lotteryCode).getDesc();
			message = String.format("%s%s出票失败,涉及%s",lotteryName,lotteryIssue ,order);
		}
		
		sendAlarmInfo(1,18, 1, message, remark+"lotto-ticket");
	}
	/**
	 * 添加报警信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月8日 下午12:18:05
	 * @param alarmType 报警类型
	 * @param alarmChild 报警子类型
	 * @param alarmLevel 报警等级
	 * @param alarmInfo 报警信息
	 * @param remark 备注
	 */
	private void sendAlarmInfo(int alarmType,int alarmChild,int alarmLevel,String alarmInfo,String remark){
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("alarmChild",alarmChild);
        jsonObject.put("alarmInfo",alarmInfo);
        jsonObject.put("alarmLevel",alarmLevel);
        jsonObject.put("alarmType",alarmType);
        jsonObject.put("remark",remark);
        jsonObject.put("alarmTime", DateUtil.getNow());
        producer.sendDataToQueue(ALARM_INFO, jsonObject.toString());
	}
	/**
	 * 发送信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年6月6日 下午2:46:45
	 * @param orders
	 * @param status
	 */
	private void send(Collection<String> orders,int status,String routing){
		if(CollectionUtils.isEmpty(orders)){
			return;
		}
		String  str= StringUtils.collectionToDelimitedString(orders,SymbolConstants.COMMA);
		producer.sendDataToRouting(TICKET_EXCHANGE,routing,getMessageStr(str, status));
	}
	/**
	 * 组装数据
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年6月5日 下午4:04:43
	 * @param orders
	 * @param status
	 * @return
	 */
	private String getMessageStr(String orders,int status){
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderCode",orders);
        jsonObject.put("buyType", "1");
        jsonObject.put("createTime", DateUtil.getNow());
        jsonObject.put("status",status);
        return jsonObject.toString();
	}
	/**
	 * 发送抄单出票成功通知
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年10月12日 下午3:25:06
	 * @param orders
	 */
	private void sendCopyOrder(Collection<String> orders) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("type", 1);
		jsonObject.put("orderCodeStr", StringUtils.collectionToDelimitedString(orders,SymbolConstants.COMMA));
		producer.sendDataToQueue(QUEUE_NAME_FOR_ORDER_COPY, jsonObject.toString());
	}
	
	/**
	 * 发送活动通知
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月27日 上午11:43:30
	 * @param orders
	 */
	private void sendActivity(Collection<String> orders,int type){
		if(CollectionUtils.isEmpty(orders)){
			return;
		}
		JSONObject message = new JSONObject();
		message.put("transId", StringUtils.collectionToDelimitedString(orders, SymbolConstants.COMMA));
		JSONObject json = new JSONObject();
		json.put("message", message);
		json.put("type", type);
		json.put("messageSource", "lotto-ticket");
		producer.sendDataToQueue(ACTIVITY_CHANNEL_QUEUE, json.toString());
	}
	
}
