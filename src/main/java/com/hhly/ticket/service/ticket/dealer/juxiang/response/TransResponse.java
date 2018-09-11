/************************************************************************/
/************************************************************************/
/* 文件名称：TransResponse.java */
/* 文件描述： 响应类*/
/* 应用平台：JAVA虚拟机 */
/* 创建时间：2016-01-16 */
/* 内部版本：V1.00 */
/* 作者：罗婷 */
/************************************************************************/
/* 修改记录：[修改日期] [修改人姓名] [外部版本] [修改标记] [修改内容] */
/* 修改记录：2016-01-16 罗婷 00.00.01 创建 */
/************************************************************************/
/************************************************************************/
package com.hhly.ticket.service.ticket.dealer.juxiang.response;

import java.util.ArrayList;
import java.util.List;

import com.hhly.ticket.service.ticket.dealer.ICheckResponse;
import com.hhly.ticket.service.ticket.dealer.ICheckTicket;
import com.hhly.ticket.service.ticket.dealer.juxiang.cathectic.JuXiangDealer;
import com.hhly.ticket.service.ticket.dealer.juxiang.request.TransRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TransResponse implements ICheckResponse{

	private String messageId; // 消息流水号
	
    private String apiCode; // 交易号
    
    private Object content; // 加密数据
    
    private String resCode; // 回复码，为"0"时表示交易成功，其他都为错误
    
    private String resMsg; // 当回复码不为"0"时，表示错误原因
    
    private String hmac; // 报文hmac-md5
    
    private String decryptContent;
    
    private String resultKey;

	public TransResponse () {
		
	}
	
    public TransResponse(TransRequest req) {
        this.apiCode = req.getApiCode();
        this.messageId = req.getMessageId();
        this.resCode = "0";
        this.resMsg = "";
        this.content = "";
    }

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getHmac() {
		return hmac;
	}

	public void setHmac(String hmac) {
		this.hmac = hmac;
	}

	/**
	 * @return the decryptContent
	 */
	public String getDecryptContent() {
		return decryptContent;
	}

	/**
	 * @param decryptContent the decryptContent to set
	 */
	public void setDecryptContent(String decryptContent) {
		this.decryptContent = decryptContent;
	}

	@Override
	public String getCode() {
		return resCode;
	}

	@Override
	public String getMessage() {
		return resMsg;
	}
	
	public String getResultKey() {
		return resultKey;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	@Override
	public List<? extends ICheckTicket> getTicket() {
		JSONObject jsonObject = JSONObject.fromObject(decryptContent);
		JSONArray array = jsonObject.getJSONArray(resultKey);
		List<CheckTicket> checkTickets = new ArrayList<>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject temp = array.getJSONObject(i);
			CheckTicket checkTicket = new CheckTicket(temp);
			checkTickets.add(checkTicket);
		}
		return checkTickets;
	}

	@Override
	public boolean isSuccess() {
		return JuXiangDealer.SUCCESS.equals(resCode);
	}

	@Override
	public boolean isExist() {
		return true;
	}
}
