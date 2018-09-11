package com.hhly.ticket.service.ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hhly.ticket.base.common.LotteryEnum.Lottery;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.IChannelManageService;
import com.hhly.ticket.service.IOrderService;
import com.hhly.ticket.service.entity.ChannelBO;
import com.hhly.ticket.service.entity.ChannelSendInfoBO;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.IConvert;
import com.hhly.ticket.service.ticket.dealer.IDealer;
import com.hhly.ticket.service.ticket.dealer.gaode.cathectic.GaoDeDealer;
import com.hhly.ticket.service.ticket.dealer.gaode.convert.BastketballGDConvert;
import com.hhly.ticket.service.ticket.dealer.gaode.convert.BjdcGDConvert;
import com.hhly.ticket.service.ticket.dealer.gaode.convert.FootballGDConvert;
import com.hhly.ticket.service.ticket.dealer.gaode.convert.LzcGDConvert;
import com.hhly.ticket.service.ticket.dealer.hengpeng.cathectic.HengPengDealer;
import com.hhly.ticket.service.ticket.dealer.hengpeng.convert.F3dHPConvert;
import com.hhly.ticket.service.ticket.dealer.hengpeng.convert.SdpokerHPConvert;
import com.hhly.ticket.service.ticket.dealer.hengpeng.convert.SsqHPConvert;
import com.hhly.ticket.service.ticket.dealer.hengpeng.convert.eleven.Sd11x5HPConvert;
import com.hhly.ticket.service.ticket.dealer.hongzhan.cathectic.HongZhanDealer;
import com.hhly.ticket.service.ticket.dealer.hongzhan.convert.DltHZConvert;
import com.hhly.ticket.service.ticket.dealer.hongzhan.convert.Gd11x5HZConvert;
import com.hhly.ticket.service.ticket.dealer.hongzhan.convert.LzcHZConvert;
import com.hhly.ticket.service.ticket.dealer.hongzhan.convert.Pl3HZConvert;
import com.hhly.ticket.service.ticket.dealer.hongzhan.convert.Pl5HZConvert;
import com.hhly.ticket.service.ticket.dealer.hongzhan.convert.QxcHZConvert;
import com.hhly.ticket.service.ticket.dealer.hongzhan.convert.SportHZConvert;
import com.hhly.ticket.service.ticket.dealer.jimi.cathectic.JiMiDealer;
import com.hhly.ticket.service.ticket.dealer.jimi.convert.BjdcJMConvert;
import com.hhly.ticket.service.ticket.dealer.jimi.convert.Cqkl10JMConvert;
import com.hhly.ticket.service.ticket.dealer.jimi.convert.DltJMConvert;
import com.hhly.ticket.service.ticket.dealer.jimi.convert.F3dJMConvert;
import com.hhly.ticket.service.ticket.dealer.jimi.convert.JX11X5JMConvert;
import com.hhly.ticket.service.ticket.dealer.jimi.convert.JXK3JMConvert;
import com.hhly.ticket.service.ticket.dealer.jimi.convert.LzcJMConvert;
import com.hhly.ticket.service.ticket.dealer.jimi.convert.Pl3JMConvert;
import com.hhly.ticket.service.ticket.dealer.jimi.convert.Pl5JMConvert;
import com.hhly.ticket.service.ticket.dealer.jimi.convert.QlcJMConvert;
import com.hhly.ticket.service.ticket.dealer.jimi.convert.QxcJMConvert;
import com.hhly.ticket.service.ticket.dealer.jimi.convert.Sd11X5JMConvert;
import com.hhly.ticket.service.ticket.dealer.jimi.convert.SportJMConvert;
import com.hhly.ticket.service.ticket.dealer.jimi.convert.SsqJMConvert;
import com.hhly.ticket.service.ticket.dealer.juxiang.cathectic.JuXiangDealer;
import com.hhly.ticket.service.ticket.dealer.juxiang.convert.Cqkl10JXConvert;
import com.hhly.ticket.service.ticket.dealer.juxiang.convert.DltJXConvert;
import com.hhly.ticket.service.ticket.dealer.juxiang.convert.F3dJXConvert;
import com.hhly.ticket.service.ticket.dealer.juxiang.convert.JXK3JXConvert;
import com.hhly.ticket.service.ticket.dealer.juxiang.convert.LzcJXConvert;
import com.hhly.ticket.service.ticket.dealer.juxiang.convert.QlcJXConvert;
import com.hhly.ticket.service.ticket.dealer.juxiang.convert.QxcJXConvert;
import com.hhly.ticket.service.ticket.dealer.juxiang.convert.SportJXConvert;
import com.hhly.ticket.service.ticket.dealer.juxiang.convert.SsqJXConvert;
import com.hhly.ticket.service.ticket.dealer.juxiang.convert.eleven.Gx11x5JXConvert;
import com.hhly.ticket.service.ticket.dealer.juxiang.convert.eleven.Sd11x5JXConvert;
import com.hhly.ticket.service.ticket.dealer.people.cathectic.PeopleDealer;
import com.hhly.ticket.service.ticket.dealer.people.convert.Convert;
import com.hhly.ticket.service.ticket.dealer.ruilang.cathectic.RuilangDealer;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.DltConvert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.F3dConvert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.LzcConvert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.Pl3Convert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.Pl5Convert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.QlcConvert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.QxcConvert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.SsqConvert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.eleven.GD11X5Convert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.eleven.JX11X5Convert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.eleven.SD11X5Convert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.eleven.XJ11X5Convert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.k3.JSK3Convert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.kl10.Cqkl10Convert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.kl10.Gdkl10Convert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.poker.SdPokerConvert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.sport.BasketballConvert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.sport.BjdcConvert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.sport.FootballConvert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.sport.GuanYaConvert;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.ssc.CqsscConvert;
import com.hhly.ticket.service.ticket.dealer.saiwei.cathectic.SaiWeiDealer;
import com.hhly.ticket.service.ticket.dealer.saiwei.convert.SportSWConvert;
import com.hhly.ticket.service.ticket.dealer.shenfu.cathectic.ShenFuDealer;
import com.hhly.ticket.service.ticket.dealer.shenfu.convert.SportSFConvert;
import com.hhly.ticket.service.ticket.dealer.tengshun.cathectic.TengShunDealer;
import com.hhly.ticket.service.ticket.dealer.tengshun.convert.A11x5TSConvert;
import com.hhly.ticket.service.ticket.dealer.tengshun.convert.DltTSConvert;
import com.hhly.ticket.service.ticket.dealer.tengshun.convert.F3dTSConvert;
import com.hhly.ticket.service.ticket.dealer.tengshun.convert.LzcTSConvert;
import com.hhly.ticket.service.ticket.dealer.tengshun.convert.Pl3TSConvert;
import com.hhly.ticket.service.ticket.dealer.tengshun.convert.Pl5TSConvert;
import com.hhly.ticket.service.ticket.dealer.tengshun.convert.QlcTSConvert;
import com.hhly.ticket.service.ticket.dealer.tengshun.convert.QxcTSConvert;
import com.hhly.ticket.service.ticket.dealer.tengshun.convert.SportTSConvert;
import com.hhly.ticket.service.ticket.dealer.tengshun.convert.SsqTSConvert;
import com.hhly.ticket.service.ticket.dealer.wencheng.cathectic.WenChengDealer;
import com.hhly.ticket.service.ticket.dealer.wencheng.convert.DltWCConvert;
import com.hhly.ticket.service.ticket.dealer.wencheng.convert.Pl3WCConvert;
import com.hhly.ticket.service.ticket.dealer.wencheng.convert.Pl5WCConvert;
import com.hhly.ticket.service.ticket.dealer.wencheng.convert.QxcWCConvert;
import com.hhly.ticket.service.ticket.dealer.wencheng.convert.SportWCConvert;
import com.hhly.ticket.service.ticket.dealer.yaosen.cathectic.YaoSenDealer;
import com.hhly.ticket.service.ticket.dealer.yaosen.convert.DltYSConvert;
import com.hhly.ticket.service.ticket.dealer.yaosen.convert.LzcYSConvert;
import com.hhly.ticket.service.ticket.dealer.yaosen.convert.Pl3YSConvert;
import com.hhly.ticket.service.ticket.dealer.yaosen.convert.Pl5YSConvert;
import com.hhly.ticket.service.ticket.dealer.yaosen.convert.QxcYSConvert;
import com.hhly.ticket.service.ticket.dealer.yaosen.convert.Sd11X5YSConvert;
import com.hhly.ticket.service.ticket.dealer.yaosen.convert.SportYSConvert;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.cathectic.YuanRunDeDealer;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.convert.GD11X5CConvert;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.convert.JcConvert;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.convert.YrdLzcConvert;
import com.hhly.ticket.service.ticket.dealer.zhongle.cathectic.ZhongLeDealer;
import com.hhly.ticket.service.ticket.dealer.zhongle.convert.SportZLConvert;
import com.hhly.ticket.service.ticket.dealer.zhongying.cathectic.ZhongYingDealer;
import com.hhly.ticket.service.ticket.dealer.zhongying.convert.SportZYConvert;
import com.hhly.ticket.service.ticket.dealer.zongguan.cathectic.ZongGuanDealer;
import com.hhly.ticket.service.ticket.dealer.zongguan.convert.BasketballZGConvert;
import com.hhly.ticket.service.ticket.dealer.zongguan.convert.FootballZGConvert;
import com.hhly.ticket.service.ticket.dealer.zongguan.convert.LzcZGConvert;

/**
 * 
 * @desc 票处理帮助类
 * @author jiangwei
 * @date 2017年5月5日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Component
public class TicketHandler {
	private static final Logger LOGGER = Logger.getLogger(TicketHandler.class);
	// 睿朗阳光出票商ID
	public static final String RUI_LANG_ID = "2";
	// 恒朋
	public static final String HENG_PENG_ID = "3";
	// 高德
	public static final String GAO_DE_ID = "4";
	// 聚享
	public static final String JU_XIANG_ID = "5";
	// 耀森
	public static final String YAO_SEN_ID = "6";
	// 吉米
	public static final String JI_MI_ID = "8";
	// 睿朗阳光出票商（积分兑换）
	public static final String JH_RUI_LANG_ID = "9";
	// 广州纵贯出票商
	public static final String ZHONG_GUAN_ID = "10";
	// 鸿展出票
	public static final String HONG_ZHAN_ID = "11";
	// 滕顺
	public static final String TENG_SHUN_ID = "12";

	public static final String ZHONG_LE_ID = "13";

	public static final String ZHONG_YING_ID = "14";

	public static final String PEOPLE_ID = "15";

	public static final String SHEN_FU_ID = "16";

	public static final String PEOPLE_2_ID = "17";

	public static final String PEOPLE_3_ID = "18";

	public static final String PEOPLE_4_ID = "19";

	public static final String PEOPLE_5_ID = "20";
	
	public static final String PEOPLE_6_ID = "21";

	public static final String PEOPLE_7_ID = "22";
	
	public static final String PEOPLE_8_ID = "23";
	
	public static final String PEOPLE_9_ID = "24";
	
	public static final String PEOPLE_10_ID = "25";
	
	public static final String PEOPLE_11_ID = "26";
	
	public static final String PEOPLE_12_ID = "27";
	
	public static final String PEOPLE_13_ID = "28";
	
	public static final String PEOPLE_14_ID = "29";
	
	public static final String SAI_WEI_ID = "30";

	public static final String WEN_CHENG_ID = "31";
	/**元润德**/
	public static final String YUAN_RUN_DE_ID = "32";

	@Autowired
	private IOrderService orderService;
	@Autowired
	private IChannelManageService channelManageService;
	@Autowired
	private IOrderDistribute distribute;
	/**
	 * 保存渠道信息 key 彩种 value 渠道信息
	 */
	private static final Map<Integer, List<ITicketChannel>> CHANNEL = new ConcurrentHashMap<>();

	/**
	 * 出票渠道获取(通过单列模式控制渠道)
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月6日 下午12:30:27
	 * @param lotteryCode
	 * @return
	 */
	public List<ITicketChannel> getChannel(int lotteryCode) {
		List<ITicketChannel> list = CHANNEL.get(lotteryCode);
		if (CollectionUtils.isEmpty(list)) {
			list = addChannel(lotteryCode);
		}
		return list;
	}

	/**
	 * 获取指定渠道出票商（不区分送票状态，用于检票）
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年7月10日 上午10:07:18
	 * @param lotteryCode
	 * @param channelId
	 * @return
	 */
	public ITicketChannel getChannelCheck(int lotteryCode, String channelId) {
		List<ITicketChannel> list = getChannel(lotteryCode);
		for (ITicketChannel channel : list) {
			if (channel.getChannelId().equals(channelId)) {
				return channel;
			}
		}
		ChannelBO channelBO = channelManageService.getChannelCheck(String.valueOf(lotteryCode), channelId);
		if (channelBO == null) {
			return null;
		}
		return createTicketChannel(channelBO);
	}

	/**
	 * 加载指定彩种的出票商渠道
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月27日 下午12:08:46
	 * @param lotteryCode
	 * @return
	 */
	private List<ITicketChannel> addChannel(int lotteryCode) {
		LOGGER.info("加载彩种出票商lotteryCode：" + lotteryCode);
		List<ITicketChannel> list = new ArrayList<>();
		List<ChannelBO> channelBOs = channelManageService.getAllChannel(String.valueOf(lotteryCode));
		for (ChannelBO channelBO : channelBOs) {
			ITicketChannel ticketChannel = createTicketChannel(channelBO);
			list.add(ticketChannel);
		}
		CHANNEL.put(lotteryCode, list);
		return list;
	}

	/**
	 * 重新初始化渠道配置信息
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月15日 上午11:39:38
	 */
	public void clearChannel(String lotteryCode) {
		if (StringUtils.isBlank(lotteryCode)) {
			Map<Integer, List<ITicketChannel>> map = new HashMap<>(CHANNEL);
			CHANNEL.clear();
			for (Map.Entry<Integer, List<ITicketChannel>> entry : map.entrySet()) {
				againDistribute(entry.getValue(), entry.getKey());
			}
		} else {
			int code = Integer.parseInt(lotteryCode);
			List<ITicketChannel> list = CHANNEL.get(code);
			CHANNEL.remove(code);
			againDistribute(list, code);
		}

	}

	/**
	 * 重新送票
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月27日 下午12:29:24
	 * @param list
	 * @param lotteryCode
	 */
	private void againDistribute(List<ITicketChannel> list, int lotteryCode) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		for (ITicketChannel iTicketChannel : list) {
			List<TicketBO> ticketBOs = iTicketChannel.clearChannelGet();
			if (CollectionUtils.isNotEmpty(ticketBOs)) {
				distribute.distribute(ticketBOs, lotteryCode,null);
			}
		}
	}

	/**
	 * 获取每一个渠道商送票信息
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月3日 下午4:25:29
	 * @return
	 */
	public Map<Integer, List<ChannelSendInfoBO>> getSendTicketInfo() {
		Map<Integer, List<ChannelSendInfoBO>> reuslt = new HashMap<>();
		for (Map.Entry<Integer, List<ITicketChannel>> entry : CHANNEL.entrySet()) {
			List<ChannelSendInfoBO> list = new ArrayList<>();
			reuslt.put(entry.getKey(), list);
			for (ITicketChannel channel : entry.getValue()) {
				ChannelSendInfoBO bo = new ChannelSendInfoBO(channel.getWeight(), channel.getTicketMoney(),
						channel.getTicketCount());
				list.add(bo);
			}
		}
		return reuslt;
	}

	/**
	 * 获取渠道商余额
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年8月9日 上午8:58:24
	 * @return
	 */
	public static Map<String, Double> getChannelBalance() {
		Map<String, Double> result = new HashMap<>();
		for (List<ITicketChannel> channelList : CHANNEL.values()) {
			for (ITicketChannel channel : channelList) {
				String channelId = channel.getChannelId();
				if (result.containsKey(channelId)) {
					continue;
				}
				try {
					result.put(channelId, channel.getChannelBalance());
				} catch (Exception e) {
					LOGGER.error("获取出票商余额错误channelId:" + channelId, e);
				}
			}
		}
		return result;
	}

	/**
	 * 创建渠道ID
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月10日 上午9:54:07
	 * @param channelBO
	 * @return
	 */
	private ITicketChannel createTicketChannel(ChannelBO channelBO) {
		IDealer dealer = getDealer(channelBO);
		IConvert convert = getConvert(channelBO.getLotteryCode(), channelBO.getTicketChannelId());
		ITicketChannel ticketChannel = new TicketChannel(convert, dealer, channelBO, orderService);
		return ticketChannel;
	}

	/**
	 * 根据彩种和渠道商获取出票接口
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月5日 下午4:39:09
	 * @param lotteryCode
	 * @param ticketChannelId
	 * @return
	 */
	private IDealer getDealer(ChannelBO channelBO) {
		IDealer dealer = null;
		Lottery lottery = Lottery.getLottery(channelBO.getLotteryCode());
		ChannnlEnum channnlEnum = getChannnlEnum(channelBO.getTicketChannelId());
		if (channnlEnum == null || (dealer = channnlEnum.getDealer(lottery, channelBO, orderService)) == null) {
			throw new ServiceRuntimeException(
					"彩种" + lottery.getDesc() + "，出票商（" + channelBO.getTicketChannelId() + "）出票渠道接口不存在");
		}
		return dealer;
	}

	/**
	 * 获取彩种票转换接口
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月5日 下午5:14:37
	 * @param lotteryCode
	 * @param ticketChannelId
	 * @return
	 */
	private IConvert getConvert(int lotteryCode, String ticketChannelId) {
		IConvert convert = null;
		Lottery lottery = Lottery.getLottery(lotteryCode);
		ChannnlEnum channnlEnum = getChannnlEnum(ticketChannelId);
		if (channnlEnum == null || (convert = channnlEnum.getConvert(lottery)) == null) {
			throw new ServiceRuntimeException("彩种" + lottery.getDesc() + "，出票商（" + ticketChannelId + "）彩种解析不存在");
		}
		return convert;
	}

	/**
	 * 枚举获取
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月5日 下午5:15:52
	 * @param ticketChannelId
	 * @return
	 */
	private static ChannnlEnum getChannnlEnum(String ticketChannelId) {
		for (ChannnlEnum dealerEnum : ChannnlEnum.values()) {
			if (dealerEnum.getTicketChannelId().equals(ticketChannelId)) {
				return dealerEnum;
			}
		}
		return null;
	}

	/**
	 * 出票商枚举
	 * 
	 * @desc
	 * @author jiangwei
	 * @date 2017年5月5日
	 * @company 益彩网络科技公司
	 * @version 1.0
	 */
	private enum ChannnlEnum {
		/**
		 * 睿朗
		 */
		RUI_LANG(RUI_LANG_ID) {
			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return new RuilangDealer(bo, orderService, 0);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				switch (lottery) {
				case SSQ:
					return new SsqConvert();
				case DLT:
					return new DltConvert();
				case SD11X5:
					return new SD11X5Convert();
				case D11X5:
					return new GD11X5Convert();
				case JX11X5:
					return new JX11X5Convert();
				case XJ11X5:
					return new XJ11X5Convert();
				case FB:
					return new FootballConvert();
				case BB:
					return new BasketballConvert();
				case JSK3:
					return new JSK3Convert();
				case F3D:
					return new F3dConvert();
				case PL3:
					return new Pl3Convert();
				case PL5:
					return new Pl5Convert();
				case ZC6:
				case JQ4:
				case SFC:
				case ZC_NINE:
					return new LzcConvert();
				case CQSSC:
					return new CqsscConvert();
				case SDPOKER:
					return new SdPokerConvert();
				case QXC:
					return new QxcConvert();
				case QLC:
					return new QlcConvert();
				case DKL10:
					return new Gdkl10Convert();
				case CQKL10:
					return new Cqkl10Convert();
				case BJDC:
				case SFGG:
					return new BjdcConvert();
				case CHP:
				case FNL:
					return new GuanYaConvert();
				default:
					break;
				}
				return null;
			}

		},
		JH_RUI_LANG(JH_RUI_LANG_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return new RuilangDealer(bo, orderService, 1);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				return RUI_LANG.getConvert(lottery);
			}

		},
		HENG_PENG(HENG_PENG_ID) {
			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return new HengPengDealer(bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				switch (lottery) {
				case SSQ:
					return new SsqHPConvert();
				case F3D:
					return new F3dHPConvert();
				case SD11X5:
					return new Sd11x5HPConvert();
				case SDPOKER:
					return new SdpokerHPConvert();
				default:
					break;
				}
				return null;
			}
		},
		GAO_DE(GAO_DE_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return new GaoDeDealer(bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				switch (lottery) {
				case BJDC:
				case SFGG:
					return new BjdcGDConvert();
				case FB:
					return new FootballGDConvert();
				case BB:
					return new BastketballGDConvert();
				case ZC6:
				case JQ4:
				case SFC:
				case ZC_NINE:
					return new LzcGDConvert();
				default:
					break;
				}
				return null;
			}

		},
		JU_XIANG(JU_XIANG_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return new JuXiangDealer(bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				switch (lottery) {
				case SD11X5:
					return new Sd11x5JXConvert();
				case DLT:
					return new DltJXConvert();
				case QXC:
					return new QxcJXConvert();
				case SSQ:
					return new SsqJXConvert();
				case QLC:
					return new QlcJXConvert();
				case F3D:
					return new F3dJXConvert();
				case JXK3:
					return new JXK3JXConvert();
				case CQKL10:
					return new Cqkl10JXConvert();
				case GX11X5:
					return new Gx11x5JXConvert();
				case FB:
				case BB:
					return new SportJXConvert();
				case SFC:
				case ZC6:
				case ZC_NINE:
				case JQ4:
					return new LzcJXConvert();
				default:
					break;
				}
				return null;
			}

		},
		YAO_SEN(YAO_SEN_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return new YaoSenDealer(bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				switch (lottery) {
				case DLT:
					return new DltYSConvert();
				case PL3:
					return new Pl3YSConvert();
				case PL5:
					return new Pl5YSConvert();
				case QXC:
					return new QxcYSConvert();
				case SD11X5:
					return new Sd11X5YSConvert();
				case FB:
				case BB:
					return new SportYSConvert();
				case SFC:
				case ZC6:
				case ZC_NINE:
				case JQ4:
					return new LzcYSConvert();
				default:
					break;
				}
				return null;
			}

		},
		JI_MI(JI_MI_ID) {
			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return new JiMiDealer(bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				switch (lottery) {
				case SD11X5:
					return new Sd11X5JMConvert();
				case JX11X5:
					return new JX11X5JMConvert();
				case JXK3:
					return new JXK3JMConvert();
				case FB:
				case BB:
					return new SportJMConvert();
				case SFC:
				case ZC6:
				case ZC_NINE:
				case JQ4:
					return new LzcJMConvert();
				case SSQ:
					return new SsqJMConvert();
				case DLT:
					return new DltJMConvert();
				case PL3:
					return new Pl3JMConvert();
				case F3D:
					return new F3dJMConvert();
				case BJDC:
				case SFGG:
					return new BjdcJMConvert();
				case QXC:
					return new QxcJMConvert();
				case QLC:
					return new QlcJMConvert();
				case PL5:
					return new Pl5JMConvert();
				case CQKL10:
					return new Cqkl10JMConvert();
				default:
					break;
				}
				return null;
			}
		},
		ZONG_GUAN(ZHONG_GUAN_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return new ZongGuanDealer(bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				switch (lottery) {
				case FB:
					return new FootballZGConvert();
				case BB:
					return new BasketballZGConvert();
				case SFC:
				case ZC6:
				case ZC_NINE:
				case JQ4:
					return new LzcZGConvert();
				default:
					break;
				}
				return null;
			}

		},
		HONG_ZHAN(HONG_ZHAN_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return new HongZhanDealer(bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				switch (lottery) {
				case FB:
				case BB:
					return new SportHZConvert();
				case SFC:
				case ZC6:
				case ZC_NINE:
				case JQ4:
					return new LzcHZConvert();
				case DLT:
					return new DltHZConvert();
				case PL3:
					return new Pl3HZConvert();
				case PL5:
					return new Pl5HZConvert();
				case D11X5:
					return new Gd11x5HZConvert();
				case QXC:
					return new QxcHZConvert();
				default:
					break;
				}
				return null;
			}

		},
		ZHONG_LE(ZHONG_LE_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return new ZhongLeDealer(bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				switch (lottery) {
				case FB:
					// case BB: 竞彩篮球检票格式有问题，格式和足球不一样<match id="180622302
					// PlayId="SF">3=2.69/0=9.05</match>
					return new SportZLConvert();
				default:
					break;
				}
				return null;
			}

		},
		ZHONG_YING(ZHONG_YING_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return new ZhongYingDealer(bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				switch (lottery) {
				case FB:
					// case BB:没有测试
					return new SportZYConvert();
				default:
					break;
				}
				return null;
			}

		},
		SHEN_FU(SHEN_FU_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return new ShenFuDealer(bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				switch (lottery) {
				case FB:
					return new SportSFConvert();
				default:
					break;
				}
				return null;
			}

		},
		PEOPLE(PEOPLE_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return new PeopleDealer(bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				switch (lottery) {
				case FB:
					return new Convert();
				default:
					break;
				}
				return null;
			}

		},
		PEOPLE_2(PEOPLE_2_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return ChannnlEnum.PEOPLE.getDealer(lottery, bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				return ChannnlEnum.PEOPLE.getConvert(lottery);
			}

		},
		PEOPLE_3(PEOPLE_3_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return ChannnlEnum.PEOPLE.getDealer(lottery, bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				return ChannnlEnum.PEOPLE.getConvert(lottery);
			}

		},
		PEOPLE_4(PEOPLE_4_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return ChannnlEnum.PEOPLE.getDealer(lottery, bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				return ChannnlEnum.PEOPLE.getConvert(lottery);
			}

		},
		PEOPLE_5(PEOPLE_5_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return ChannnlEnum.PEOPLE.getDealer(lottery, bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				return ChannnlEnum.PEOPLE.getConvert(lottery);
			}

		},
		PEOPLE_6(PEOPLE_6_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return ChannnlEnum.PEOPLE.getDealer(lottery, bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				return ChannnlEnum.PEOPLE.getConvert(lottery);
			}

		},
		PEOPLE_7(PEOPLE_7_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return ChannnlEnum.PEOPLE.getDealer(lottery, bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				return ChannnlEnum.PEOPLE.getConvert(lottery);
			}

		},
		PEOPLE_8(PEOPLE_8_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return ChannnlEnum.PEOPLE.getDealer(lottery, bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				return ChannnlEnum.PEOPLE.getConvert(lottery);
			}

		},
		PEOPLE_9(PEOPLE_9_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return ChannnlEnum.PEOPLE.getDealer(lottery, bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				return ChannnlEnum.PEOPLE.getConvert(lottery);
			}

		},
		PEOPLE_10(PEOPLE_10_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return ChannnlEnum.PEOPLE.getDealer(lottery, bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				return ChannnlEnum.PEOPLE.getConvert(lottery);
			}

		},
		PEOPLE_11(PEOPLE_11_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return ChannnlEnum.PEOPLE.getDealer(lottery, bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				return ChannnlEnum.PEOPLE.getConvert(lottery);
			}

		},
		PEOPLE_12(PEOPLE_12_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return ChannnlEnum.PEOPLE.getDealer(lottery, bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				return ChannnlEnum.PEOPLE.getConvert(lottery);
			}

		},
		PEOPLE_13(PEOPLE_13_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return ChannnlEnum.PEOPLE.getDealer(lottery, bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				return ChannnlEnum.PEOPLE.getConvert(lottery);
			}

		},
		PEOPLE_14(PEOPLE_14_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return ChannnlEnum.PEOPLE.getDealer(lottery, bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				return ChannnlEnum.PEOPLE.getConvert(lottery);
			}

		},
		SAI_WEI(SAI_WEI_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return new SaiWeiDealer(bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				switch (lottery) {
				case FB:
					return new SportSWConvert();
				default:
					break;
				}
				return null;
			}
		},
		WEN_CHENG(WEN_CHENG_ID) {

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return new WenChengDealer(bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				switch (lottery) {
				case FB:
					return new SportWCConvert();
				case DLT:
					return new DltWCConvert();
				case QXC:
					return new QxcWCConvert();
				case PL3:
					return new Pl3WCConvert();
				case PL5:
					return new Pl5WCConvert();
				default:
					break;
				}
				return null;
			}
		},
		TENG_SHUN(TENG_SHUN_ID) {
			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return new TengShunDealer(bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				switch (lottery) {
				case DLT:
					return new DltTSConvert();
				case SSQ:
					return new SsqTSConvert();
				case QLC:
					return new QlcTSConvert();
				case PL5:
					return new Pl5TSConvert();
				case PL3:
					return new Pl3TSConvert();
				case F3D:
					return new F3dTSConvert();
				case QXC:
					return new QxcTSConvert();
				// case SDPOKER:
				// return new PokerTSConvert();
				// case JXK3:
				// case SHK3:
				// return new Ak3TSConvert();
				case D11X5:
					// case JX11X5:
					// case SD11X5:
					// case HB11X5:
					// case SH11X5:
					// case ZJ11X5:
					return new A11x5TSConvert();
				case FB:
				case BB:
					return new SportTSConvert();
				case SFC:
				case ZC6:
				case ZC_NINE:
				case JQ4:
					return new LzcTSConvert();
				default:
					break;
				}
				return null;
			}
		},
		YUAN_RUN_DE(YUAN_RUN_DE_ID){

			@Override
			public IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService) {
				return new YuanRunDeDealer(bo, orderService);
			}

			@Override
			public IConvert getConvert(Lottery lottery) {
				switch (lottery) {
					case D11X5:
						return new GD11X5CConvert();
					case FB:
					case BB:
						return new JcConvert();
					case SFC:
					case ZC_NINE:
						return new YrdLzcConvert();
					default:
						break;
				}
				return null;
			}
			
		};
		ChannnlEnum(String ticketChannelId) {
			this.ticketChannelId = ticketChannelId;
		}

		public String ticketChannelId;

		public String getTicketChannelId() {
			return ticketChannelId;
		}

		/**
		 * 获取出票商出票接口
		 * 
		 * @author jiangwei
		 * @Version 1.0
		 * @CreatDate 2017年5月5日 下午5:01:50
		 * @param lottery
		 * @return
		 */
		public abstract IDealer getDealer(Lottery lottery, ChannelBO bo, IOrderService orderService);

		/**
		 * 出票商接口装换接口
		 * 
		 * @author jiangwei
		 * @Version 1.0
		 * @CreatDate 2017年5月5日 下午5:02:21
		 * @param lottery
		 * @return
		 */
		public abstract IConvert getConvert(Lottery lottery);

	}

}
