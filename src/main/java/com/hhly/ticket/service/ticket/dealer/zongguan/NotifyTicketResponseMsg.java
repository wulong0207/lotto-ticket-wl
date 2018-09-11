package com.hhly.ticket.service.ticket.dealer.zongguan;

import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.hhly.ticket.service.ticket.dealer.zongguan.response.NotifyTicketBody;
import com.hhly.ticket.util.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @desc 投注相应
 * @author jiangwei
 * @date 2018年3月16日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("msg")
public class NotifyTicketResponseMsg extends AbstractMsg implements ICheckResponse{
	private static XStream XS = XmlUtil.createXStream(NotifyTicketResponseMsg.class);

	
	private NotifyTicketBody body;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T fromXML(String strXml) {
		return (T) XS.fromXML(strXml);
	}


	/**
	 * @return the body
	 */
	public NotifyTicketBody getBody() {
		return body;
	}


	/**
	 * @param body the body to set
	 */
	public void setBody(NotifyTicketBody body) {
		this.body = body;
	}
	@Override
	public String getCode() {
		return getHead().getTranscode();
	}


	@Override
	public String getMessage() {
		return null;
	}


	@Override
	public List<? extends ICheckTicket> getTicket() {
		return body.getTicketresults();
	}


	@Override
	public boolean isSuccess() {
		return "007".equals(getCode());
	}


	@Override
	public boolean isExist() {
		return isSuccess();
	}

	public static void main(String[] args) {
	   String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><head transcode=\"007\" partnerid=\"10027\" version=\"1.0\" time=\"20180320155942\" /><body><ticketresults><ticketresult lotteryId=\"JCLQFH\" ticketId=\"BB18032015591607200003\" palmId=\"642227\" statusCode=\"003\" message=\"交易成功\" printodd=\"SF@3-301:[主胜=1.120,客胜=3.900]/RFSF@3-302:[主胜=1.750,客胜=1.750^+4.50]/DXF@3-303:[大=1.750,小=1.750^161.5]/FC@3-304:[胜1-5分=3.650,胜6-10分=4.050,胜11-15分=7.200,负1-5分=4.100]\" Unprintodd=\"1.120,3.900;1.750,1.750^+4.50;1.750,1.750^161.5;3.650,4.050,7.200,4.100\" printNo=\"62-8E69-92AE632477C8\" maxBonus=\"0.00\" PrintOutTime=\"2018/3/20 15:58:15\" rq=\"\" ProvinceId=\"\" /></ticketresults></body></msg>";
	   NotifyTicketResponseMsg msg = new NotifyTicketResponseMsg().fromXML(str);
	   System.out.println(msg.getBody().getTicketresults().get(0).getBatchNum());
	}
	
}
