package com.hhly.ticket.util;

import java.util.Base64;

import com.hhly.ticket.base.exception.ServiceRuntimeException;

public class Base64Util {
	final static Base64.Decoder decoder = Base64.getDecoder();
	
	final static Base64.Encoder encoder = Base64.getEncoder();
	/**
	 * 编码
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年7月3日 下午3:31:53
	 * @param text
	 * @return
	 */
	public static String encoder(String text){
		try {
			byte[] textByte = text.getBytes("UTF-8");
			return encoder.encodeToString(textByte);
		} catch (Exception e) {
			throw new ServiceRuntimeException("加密错误", e);
		}
		
	}
	/**
	 * 解码
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年7月3日 下午3:32:00
	 * @param text
	 * @return
	 */
	public static String decoder(String text){
		try {
			return new String(decoder.decode(text), "UTF-8");
		} catch (Exception e) {
			throw new ServiceRuntimeException("解密密错误", e);
		}
	}
}
