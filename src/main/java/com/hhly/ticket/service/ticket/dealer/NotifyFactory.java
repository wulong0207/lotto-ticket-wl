package com.hhly.ticket.service.ticket.dealer;

import java.util.HashMap;
import java.util.Map;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.IChannelManageService;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.ticket.TicketHandler;
import com.hhly.ticket.service.ticket.dealer.gaode.cathectic.GaoDeDealer;
import com.hhly.ticket.service.ticket.dealer.hengpeng.cathectic.HengPengDealer;
import com.hhly.ticket.service.ticket.dealer.jimi.cathectic.JiMiDealer;
import com.hhly.ticket.service.ticket.dealer.juxiang.cathectic.JuXiangDealer;
import com.hhly.ticket.service.ticket.dealer.ruilang.cathectic.RuilangDealer;
import com.hhly.ticket.service.ticket.dealer.saiwei.cathectic.SaiWeiDealer;
import com.hhly.ticket.service.ticket.dealer.tengshun.cathectic.TengShunDealer;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.cathectic.YuanRunDeDealer;
import com.hhly.ticket.service.ticket.dealer.zongguan.cathectic.ZongGuanDealer;
/**
 * @desc 各个出票商回调接口工厂类
 * @author jiangwei
 * @date 2017年8月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class NotifyFactory {
	/**
	 * 保存出票商回调通知接口
	 * key:出票商ID
	 * value:接口
	 */
	private static  final  Map<String,INotify>  NOTIFY = new HashMap<>();
	/**
	 * 获取出票商信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月14日 下午4:58:57
	 * @param channelId
	 * @param channelManageService
	 * @param orderService
	 * @return
	 */
	public static INotify getNotify(String channelId,IChannelManageService channelManageService,IOrderService orderService){
		INotify notify =  NOTIFY.get(channelId);
		if(notify == null){
			synchronized (NotifyFactory.class) {
				if(notify == null){
					ChannelBO bo  = channelManageService.getChannel(channelId);
					switch (channelId) {
					case TicketHandler.RUI_LANG_ID:
						notify = new RuilangDealer(bo.getDrawerAccount(),bo.getAccountPassword(),orderService);
						break;
					case TicketHandler.HENG_PENG_ID:
						notify = new HengPengDealer(bo.getDrawerAccount(),bo.getAccountPassword(),orderService);
						break;
					case TicketHandler.GAO_DE_ID:
						notify = new GaoDeDealer(bo.getDrawerAccount(),bo.getAccountPassword(),orderService);
						break;
					case TicketHandler.JU_XIANG_ID:
						notify = new JuXiangDealer(bo.getDrawerAccount(),bo.getAccountPassword(),orderService);
						break;
					case TicketHandler.JI_MI_ID:
						notify = new JiMiDealer(bo.getDrawerAccount(),bo.getAccountPassword(),orderService);
						break;
					case TicketHandler.ZHONG_GUAN_ID:
						notify = new ZongGuanDealer(bo.getDrawerAccount(),bo.getAccountPassword(),orderService);
						break;
					case TicketHandler.TENG_SHUN_ID:
						notify = new TengShunDealer(bo.getDrawerAccount(),bo.getAccountPassword(),orderService);
						break;
					case TicketHandler.SAI_WEI_ID:
						notify = new SaiWeiDealer(bo.getDrawerAccount(),bo.getAccountPassword(),bo.getAuthCode(),orderService);
						break;
					case TicketHandler.YUAN_RUN_DE_ID:
						notify = new YuanRunDeDealer(bo,orderService);
						break;
					default:
						throw new ServiceRuntimeException("不存在该出票商");
					}
					NOTIFY.put(channelId, notify);
				}
			}
		}
		return notify;
	}
}
