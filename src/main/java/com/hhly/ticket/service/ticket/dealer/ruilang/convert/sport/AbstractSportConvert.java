package com.hhly.ticket.service.ticket.dealer.ruilang.convert.sport;

import java.util.HashMap;
import java.util.Map;

import com.hhly.ticket.base.common.SymbolConstants;
import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;
import com.hhly.ticket.service.ticket.dealer.ruilang.convert.AbstractConvert;
import com.hhly.ticket.util.DateUtil;
/**
 * @desc 竞彩彩格式转换
 * @author jiangwei
 * @date 2017年5月12日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class AbstractSportConvert extends AbstractConvert {
	//竞技彩串关对应出票商代码
	private  static  final Map<String,String> WAY_VIE;

	static{
		WAY_VIE = new HashMap<String,String>(61){
		private static final long serialVersionUID = 4304433803794645974L;
		{
			put("1_1","01");
			put("2_1","02");
			put("3_1","03");
			put("3_3","04");
			put("3_4","05");
			put("4_1","06");
			put("4_4","07");
			put("4_5","08");
			put("4_6","09");
			put("4_11","10");
			put("5_1","11");
			put("5_5","12");
			put("5_6","13");
			put("5_10","14");
			put("5_16","15");
			put("5_20","16");
			put("5_26","17");
			put("6_1","18");
			put("6_6","19");
			put("6_7","20");
			put("6_15","21");
			put("6_20","22");
			put("6_22","23");
			put("6_35","24");
			put("6_42","25");
			put("6_50","26");
			put("6_57","27");
			put("7_1","28");
			put("7_7","29");
			put("7_8","30");
			put("7_21","31");
			put("7_35","32");
			put("7_120","33");
			put("8_1","34");
			put("8_8","35");
			put("8_9","36");
			put("8_28","37");
			put("8_56","38");
			put("8_70","39");
			put("8_247","40");
		}};
	}
    
	@Override
	protected String getChildClass(TicketBO bo) {	
		return null;
	}
	
	@Override
	protected String getLotteryCode(TicketBO bo) {
		 boolean single = bo.getChannelTicketContent().startsWith("01");
		 return getSportCode(single, bo.getLotteryChildCode());
	}
	@Override
	protected String getLotteryIssye(TicketBO bo) {
		//取最大的比赛场次，最后一个
		String[] str = bo.getBuyScreen().split(SymbolConstants.COMMA);
		return "20" + str[str.length-1];
	}
	/**
	 * 计算玩法
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月16日 下午2:39:08
	 * @param single
	 * @param lotteryChildCode
	 * @return
	 */
	protected abstract String getSportCode(boolean single,int lotteryChildCode);
	
	@Override
	protected String getContent(String childClass, TicketBO bo) {
		String[] str = bo.getTicketContent().split(SymbolConstants.UP_CAP_DOUBLE_SLASH);
		if(str.length < 2){
			throw new ServiceRuntimeException("足彩格式错误");
		}
		
		String way = getWay(str[1]);
		StringBuilder sb = new StringBuilder();
		boolean single = false;//是否是单关 
		if("01".equals(way)){
			single = true;
			sb.append("01-");
		}else{
			sb.append("02-");
		}
		sb.append(way);
		sb.append("-");
		sb.append(doContent(str[0], single,bo.getLotteryChildCode()));
		sb.append("|");
		sb.append(DateUtil.convertDateToStr(bo.getStartMatchTime(),"MMddHHmm"));
		sb.append("-");
		sb.append(bo.getMultipleNum());
		sb.append("-");
		sb.append((int)bo.getTicketMoney());
		return sb.toString();
	}
	/**
	 * 对格式进行装换
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月12日 下午6:01:58
	 * @param primary 内容
	 * @param single 是否单关
	 * @param lotteryChildCode 子玩法
	 * @return
	 */
	private String doContent(String primary, boolean single,int lotteryChildCode) {
		String  channelCode = getSportCode(single, lotteryChildCode);
		char[] bytes = primary.toCharArray();
		//1611281001[+1](3@1.57,0@2.27)|1611281002[+1](1@1.89,0@4.21)|1611281003[+1](0@4.21) 
		//->201102035001:[3,0]/ 201102036002:[0]/ 201102036003:[1,0]
		
		//1611281001_S(1@1.89,0@4.21)|1611281002_S(1@1.89,0@4.21)|1611281003_Z(0@4.21)|1611281004_R[-2](3@3.33) 
		//->彩种 ID=场次 1:[选项 1,选项 2]/彩种 ID=场次 2:[选项 1,选项 2]
		boolean isAdd = true;
		StringBuilder allContent = new StringBuilder();
		StringBuilder content = new StringBuilder("20");
		content.append(bytes[0]);
		//循环遍历每一个字节进行处理
		for (int i = 1; i < bytes.length; i++) {
			char b = bytes[i];
			switch (b) {
			case '[':
				isAdd = false;
				continue;
			case ']':
				isAdd = true;
				continue;
			case '(':
				content.append(":[");
				isAdd = true;
				continue;
			case ')':
				content.append("]");
				isAdd = false;
				continue;
			case ',':
				isAdd = true;
				break;
			case '@':
				isAdd = false;
				continue;
			case '|':
				if(allContent.length()>0){
					allContent.append("/");
				}
				String str = disposeContent(content.toString(),channelCode);
				allContent.append(str);
				content.setLength(0);
				content.append("20");
				isAdd = true;
				continue;
			case '_':
				isAdd = false;
				continue;
			case 'S':
				content.insert(0, "=");
				channelCode = getContentS(single);
				content.insert(0, channelCode);
				continue;
			case 'R':
				content.insert(0, "=");
				channelCode = getContentR(single);
				content.insert(0, channelCode);
				continue;
			case 'Q':
				content.insert(0, "=");
				if(single){
					channelCode = "312";
				}else{
					channelCode = "302";
				}
				content.insert(0,channelCode);
				continue;
			case 'Z':
				content.insert(0, "=");
				if(single){
					channelCode = "313";
				}else{
					channelCode = "303";
				}
				content.insert(0,channelCode);
				continue;
			case 'B':
				content.insert(0, "=");
				if(single){
					channelCode = "314";
				}else{
					channelCode = "304";
				}
				content.insert(0,channelCode);
				continue;
			case 'C':
				content.insert(0, "=");
				if(single){
					channelCode = "318";
				}else{
					channelCode = "308";
				}
				content.insert(0,channelCode);
				continue;
			case 'D':
				content.insert(0, "=");
				if(single){
					channelCode = "319";
				}else{
					channelCode = "309";
				}
				content.insert(0,channelCode);
				continue;
			default:
				break;
			}
			if(isAdd){
				content.append(b);
			}
		}
		String str = disposeContent(content.toString(),channelCode);
		if(allContent.length()==0){
			return str;
		}else{
			allContent.append("/");
			allContent.append(str);
			return allContent.toString();
		}
	}
	
	/**
	 * 获取竞技彩过关方式对应的选号方式
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月12日 下午4:18:31
	 * @param way
	 * @return
	 */
	protected String getWay(String way){
		return WAY_VIE.get(way);
	}
	/**
	 * 对装换后的格式在进行2次的处理，（投注选项如：本站99表示大，出票商1表示大）
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月23日 上午11:29:42
	 * @param content
	 * @param channelCode
	 * @return
	 */
	protected String disposeContent(String content, String channelCode){
		return content;
	}
	/**
	 * 解析‘R’字符串
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月15日 上午10:04:45
	 * @param single
	 * @return
	 */
	protected abstract String getContentR(boolean single);

	/**
	 * 解析‘S’字符串
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月15日 上午9:59:22
	 * @param single 是否是单关
	 * @return
	 */
	protected abstract String getContentS(boolean single);
}
