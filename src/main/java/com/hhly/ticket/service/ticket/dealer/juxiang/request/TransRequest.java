/************************************************************************/
/************************************************************************/
/* 文件名称：TransRequest.java */
/* 文件描述： 请求类*/
/* 应用平台：JAVA虚拟机 */
/* 创建时间：2016-01-16 */
/* 内部版本：V1.00 */
/* 作者：罗婷 */
/************************************************************************/
/* 修改记录：[修改日期] [修改人姓名] [外部版本] [修改标记] [修改内容] */
/* 修改记录：2016-01-16 罗婷 00.00.01 创建 */
/************************************************************************/
/************************************************************************/
package com.hhly.ticket.service.ticket.dealer.juxiang.request;

import java.util.Map;
import java.util.TreeMap;

public class TransRequest {
	
	private String version; // 版本号
	
    private String apiCode; // 交易号
    
    private String partnerId; // 渠道商ID
    
    private String messageId; // 消息流水号
    
    private Object content; // 加密数据
    
    private String hmac; // 报文hmac-md5,所有其他字段内容按字段名首字母序排列连接之后通过hmac-md5算法计算得到的摘要值

	private Map<String, Object> map = new TreeMap<String, Object>(); // 供hmac排序用

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
        map.put("apiCode", apiCode);
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
        map.put("content", content);
    }

	public String getVersion() {
		return version;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setVersion(String version) {
		this.version = version;
        map.put("version", version);
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
        map.put("partnerId", partnerId);
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
        map.put("messageId", messageId);
	}

	public String getHmac() {
		return hmac;
	}

	public void setHmac(String hmac) {
		this.hmac = hmac;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
