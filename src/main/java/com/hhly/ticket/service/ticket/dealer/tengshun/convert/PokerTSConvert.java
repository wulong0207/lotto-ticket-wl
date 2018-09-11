package com.hhly.ticket.service.ticket.dealer.tengshun.convert;

import java.util.HashMap;
import java.util.Map;

import com.hhly.ticket.base.exception.ServiceRuntimeException;
import com.hhly.ticket.service.entity.TicketBO;

public class PokerTSConvert extends AbstractConvert {

	private Map<String, String> contentMap;

	public PokerTSConvert() {
		contentMap = new HashMap<>();
		contentMap.put("A", "01");
		contentMap.put("2", "02");
		contentMap.put("3", "03");
		contentMap.put("4", "04");
		contentMap.put("5", "05");
		contentMap.put("6", "06");
		contentMap.put("7", "07");
		contentMap.put("8", "08");
		contentMap.put("9", "09");
		contentMap.put("10", "10");
		contentMap.put("J", "11");
		contentMap.put("Q", "12");
		contentMap.put("K", "13");

	}

	@Override
	protected String getLotteryIssye(TicketBO bo) {
		return "20" + bo.getLotteryIssue();
	}

	@Override
	protected String getContent(TicketBO bo) {
		String type = getType(bo.getContentType());
		String play = play(bo);
		if (Integer.parseInt(play) <= 6) {
			return optional(bo.getTicketContent(), type, play);
		} else {
			return other(bo.getTicketContent(), type, play);
		}
	}
    /**
     * 其它玩法
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2018年5月19日 下午3:27:05
     * @param ticketContent
     * @param type
     * @param play
     * @return
     */
	private String other(String ticketContent, String type, String play) {
		String[] sp = ticketContent.split(";");
		StringBuilder sb = new StringBuilder();
		for (String str : sp) {
			if (sb.length() > 0) {
				sb.append(contentEnd(play, type));
				sb.append(";");
			}
			if ("7".equals(play) || "8".equals(play)) {
				String cn = str.substring(0, 1);
				if ("A".equals(cn)) {
					sb.append("0");
					sb.append(play);
					type = "3";
				} else {
					sb.append("0");
					sb.append(cn);
				}
			} else if ("9".equals(play) || "11".equals(play) || "10".equals(play)) {
				String cn = str.split(",")[0];
				if ("X".equals(cn)) {
					if ("9".equals(play)) {
						sb.append("09");
					} else {
						sb.append("11");
					}
					type = "3";
				} else if ("Y".equals(cn)) {
					sb.append("10");
					type = "3";
				} else {
					sb.append(contentMap.get(cn));
				}
			}
		}
		sb.append(contentEnd(play, type));
		return sb.toString();
	}

	/**
	 * 任选
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年5月19日 下午2:56:10
	 * @param ticketContent
	 * @param type
	 * @param play
	 * @return
	 */
	private String optional(String ticketContent, String type, String play) {
		StringBuilder sb = new StringBuilder();
		for (char c : ticketContent.toCharArray()) {
			switch (c) {
			case 'A':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case 'J':
			case 'Q':
			case 'K':
				sb.append(contentMap.get(String.valueOf(c)));
				break;
			case '#':
				sb.append("$");
				break;
			case ';':
				sb.append(contentEnd(play, type));
				sb.append(";");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		sb.append(contentEnd(play, type));
		return sb.toString();
	}

	/**
	 * 出票商玩法
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年5月19日 下午2:56:23
	 * @param childCode
	 * @return
	 */
	@Override
	public String play(TicketBO bo) {
		switch (bo.getLotteryChildCode()) {
		case 22501:// 山东快乐扑克3任1
			return "1";
		case 22502:// 山东快乐扑克3任2
			return "2";
		case 22503:// 山东快乐扑克3任3
			return "3";
		case 22504:// 山东快乐扑克3任4
			return "4";
		case 22505:// 山东快乐扑克3任5
			return "5";
		case 22506:// 山东快乐扑克3任6
			return "6";
		case 22507:// 山东快乐扑克3同花
			return "7";
		case 22508:// 山东快乐扑克3顺子
			return "9";
		case 22509:// 山东快乐扑克3对子
			return "11";
		case 22510:// 山东快乐扑克3豹子
			return "10";
		case 22511:// 山东快乐扑克3同花顺
			return "8";
		default:
			throw new ServiceRuntimeException("山东快乐扑克不存在玩法");
		}
	}

}
