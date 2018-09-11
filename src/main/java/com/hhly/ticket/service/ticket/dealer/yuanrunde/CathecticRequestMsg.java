package com.hhly.ticket.service.ticket.dealer.yuanrunde;

import com.hhly.ticket.service.ticket.dealer.yuanrunde.request.CathecticHead;
import com.hhly.ticket.service.ticket.dealer.yuanrunde.util.YuanRunDeUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 投注实体对象
 * @author wul687
 */
@XStreamAlias("content")
public class CathecticRequestMsg  extends AbstractXml{
	
	private CathecticHead head;
	
	private String body;
	/**
	 * 校验码(MD5(业务编码+发起请求的时间+商户编码+密钥))
	 */
	private String signature;
	
	public String getSignature() {
		return signature;
	}
	public CathecticHead getHead() {
		return head;
	}
	public void setHead(CathecticHead head) {
		this.head = head;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}

	public static void main(String[] args) {
//		try {
//			CathecticRequestMsg msg = new CathecticRequestMsg();
//			CathecticHead head = new CathecticHead();
//			head.setCommandCode("104");//2001
//			head.setMerchantCode("shyc1536");
//			head.setTimestamp(DateUtil.getNow("yyyyMMddHHmmss"));
//			head.setMsgId(head.getMerchantCode()+head.getCommandCode()+head.getTimestamp());
//			String ylSignature = head.getCommandCode()+head.getTimestamp()+head.getMerchantCode()+"rnpma9tlwxf9as5w";
////			System.out.println(ylSignature);
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			md.update(ylSignature.getBytes("UTF-8"));
//			byte[] b = md.digest();
//			msg.setSignature(MD5Encoder.encode(b));
////			System.out.println(msg.getSignature());
//			msg.setHead(head);
//			
//			CathecticMessage cathecticMessage = new CathecticMessage();
//			
//			List<CathecticOrder> orders = new ArrayList<>();
//			CathecticOrder cathecticOrder = new CathecticOrder();
//			cathecticOrder.setAdd("0");
//			cathecticOrder.setBetcode("01,02^");
//			cathecticOrder.setId(System.currentTimeMillis()+"");
//			cathecticOrder.setPlayerId("200102");
//			cathecticOrder.setMoney("2");
//			cathecticOrder.setMultiple("1");
//			cathecticOrder.setSaleId("2001");
//			cathecticOrder.setTermNum("18080959");
//			cathecticOrder.setEndtime("2018-08-09 16:08:20");
//			orders.add(cathecticOrder);
//			cathecticMessage.setOrders(orders);
//			String bodyMessage = cathecticMessage.toXml();
//			bodyMessage = bodyMessage.replace("<?xml version=\"1.0\" ?>", "");
//			bodyMessage = "<message><content><merchantCode>shyc1536</merchantCode></content></message>";
////			System.out.println(bodyMessage);
//			msg.setBody(YuanRunDeUtil.desEncrypt(bodyMessage,"rnpma9tlwxf9as5w"));
//	        String string = msg.toXml();
//	        String requestResult = string.replace("<?xml version=\"1.0\" ?>", "<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
////	        System.out.println(requestResult);
//	        try {
//	        	Map<String, String> map = new HashMap<>();
//	        	map.put("xml", requestResult);
//	        	String result = HttpUtil.doPostCharset("http://39.106.163.44/submitInterface.action", "utf-8", map);
//	        	System.out.println(result);
//	        	CathecticReponseMsg cathecticReponseMsg = new CathecticReponseMsg().fromXML(result);
//	        	String bodyDecrypt = YuanRunDeUtil.desDecrypt(cathecticReponseMsg.getBody(), "rnpma9tlwxf9as5w");
//	        	System.out.println(bodyDecrypt);
//	        	//<message><merchantCode>shyc1536</merchantCode><balance>9994.0</balance></message>
//	        	Document document = DocumentHelper.parseText(bodyDecrypt);
//	        	Element ele = document.getRootElement();
//	        	System.out.println(ele.element("balance").getText());
//	        } catch (Exception e) {
//	        	e.printStackTrace();
//	        }
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		String b = "ePDcykR7lIb85hC/R79lnHkwSC4yOIO7kRFtgCtlhj4WeBRs9CQM/aWv+P+H9uD5X39BKlgM6wJV+UMqEMOQJPnhCWvmW5uUskccDzHEMePUkw7CFavcJw==";
		System.out.println(YuanRunDeUtil.desDecrypt(b, "rnpma9tlwxf9as5w"));
		
//		try {
//			CathecticRequestMsg msg = new CathecticRequestMsg();
//			CathecticHead head = new CathecticHead();
//			head.setCommandCode("104");//2001
//			head.setMerchantCode("shyc1536");
//			head.setTimestamp(DateUtil.getNow("yyyyMMddHHmmss"));
//			head.setMsgId(head.getMerchantCode()+head.getCommandCode()+head.getTimestamp());
//			String ylSignature = head.getCommandCode()+head.getTimestamp()+head.getMerchantCode()+"rnpma9tlwxf9as5w";
//			System.out.println(ylSignature);
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			md.update(ylSignature.getBytes("UTF-8"));
//			byte[] b = md.digest();
//			msg.setSignature(MD5Encoder.encode(b));
//			System.out.println(msg.getSignature());
//			msg.setHead(head);
//			
//			CathecticMessage cathecticMessage = new CathecticMessage();
//			
//			List<CathecticOrder> orders = new ArrayList<>();
//			CathecticOrder cathecticOrder = new CathecticOrder();
//			cathecticOrder.setAdd("0");
//			cathecticOrder.setBetcode("300211001-20180809002(25)^");
//			cathecticOrder.setId(System.currentTimeMillis()+"");
//			cathecticOrder.setPlayerId("3002");
//			cathecticOrder.setMoney("2");
//			cathecticOrder.setMultiple("1");
//			cathecticOrder.setSaleId("3002");
//			cathecticOrder.setTermNum("20180809");
//			cathecticOrder.setEndtime("2018-08-10 00:00:00");
//			orders.add(cathecticOrder);
//			cathecticMessage.setOrders(orders);
//			String bodyMessage = cathecticMessage.toXml();
//			bodyMessage = bodyMessage.replace("<?xml version=\"1.0\" ?>", "");
////			bodyMessage = "<message><content><merchantCode>shyc1536</merchantCode></content></message>";
//			System.out.println(bodyMessage);
//			msg.setBody(YuanRunDeUtil.desEncrypt(bodyMessage,"rnpma9tlwxf9as5w"));
//	        String string = msg.toXml();
//	        String requestResult = string.replace("<?xml version=\"1.0\" ?>", "<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
//	        System.out.println(requestResult);
//	        try {
//	        	Map<String, String> map = new HashMap<>();
//	        	map.put("xml", requestResult);
//	        	String result = HttpUtil.doPostCharset("http://39.106.163.44/submitInterface.action", "utf-8", map);
//	        	
//	        	System.out.println(result);
//	        } catch (Exception e) {
//	        	e.printStackTrace();
//	        }
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
