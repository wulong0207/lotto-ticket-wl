/************************************************************************/
/************************************************************************/
/* 文件名称：TransUtil.java */
/* 文件描述：报文加密工具类*/
/* 应用平台：JAVA虚拟机 */
/* 创建时间：2016-01-16 */
/* 内部版本：V1.00 */
/* 作者：罗婷 */
/************************************************************************/
/* 修改记录：[修改日期] [修改人姓名] [外部版本] [修改标记] [修改内容] */
/* 修改记录：2016-01-16 罗婷 00.00.01 创建 */
/************************************************************************/
/************************************************************************/
package com.hhly.ticket.service.ticket.dealer.juxiang.util;

import java.util.Map;
import java.util.TreeMap;

import com.hhly.ticket.service.ticket.dealer.juxiang.response.TransResponse;

public class TransUtil {
	/**
     * 根据key按字典序拼接内容生成报文头hmac
     * @param map
     * @param key
     * @return hmac
     */
    public static String createHMac(Map<String, Object> map, String key) {
    	StringBuffer paramSB = new StringBuffer();
    	//拼接除hmac外的字段的内容，生成报文hmac
	    for (Map.Entry<String, Object> entry : map.entrySet()) {
	    	if (null != entry.getValue() && !"hmac".equals(entry.getKey())) {
	    		paramSB.append(entry.getValue().toString());
	    	}
	    }
    	String hmac = Digest.hmacSign(paramSB.toString(), key);
    	return hmac;
    }
    
    /**
     * 根据key按字典序拼接内容生成报文头hmac
     * @param transResponse
     * @param key
     * @return hmac
     */
    public static String createHMac(TransResponse transResponse, String key) {
    	Map<String, Object> map = new TreeMap<String, Object>();
    	map.put("apiCode", transResponse.getApiCode());
    	map.put("messageId", transResponse.getMessageId());
    	map.put("resCode", transResponse.getResCode());
    	map.put("resMsg", transResponse.getResMsg());
    	map.put("content", transResponse.getContent());    	
    	return createHMac(map, key);
    }
    
    /**
     * 根据加密消息体的key获取hmac的key
     * @return key
     */
    public static String getHMacKey(String key) {
    	String hmacKey = key.substring(0, 16);
    	return hmacKey;
    }
}
