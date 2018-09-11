/************************************************************************/
/************************************************************************/
/* 文件名称：Digest.java */
/* 文件描述： hmac的签名算法工具类*/
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
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.log4j.Logger;

public class Digest {
	public static final String ENCODE = "UTF-8"; // UTF-8


	private static Logger logger = Logger.getLogger(Digest.class);
	
	/**
	 * 对报文进行hmac签名，字符串按照UTF-8编码
	 * 
	 * @param aValue
	 *            - 字符串
	 * @param aKey
	 *            - 密钥
	 * @return - 签名结果，hex字符串
	 */
	public static String hmacSign(String aValue, String aKey) {
		return hmacSign(aValue, aKey, ENCODE);
	}

	/**
	 * 对报文进行采用MD5进行hmac签名
	 * 
	 * @param aValue
	 *            - 字符串
	 * @param aKey
	 *            - 密钥
	 * @param encoding
	 *            - 字符串编码方式
	 * @return - 签名结果，hex字符串
	 */
	public static String hmacSign(String aValue, String aKey, String encoding) {
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes(encoding);
			value = aValue.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}
		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.info("发生异常" + e);
			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return toHex(dg);
	}

	/**
	 * 对报文进行采用SHA进行hmac签名
	 * 
	 * @param aValue
	 *            - 字符串
	 * @param aKey
	 *            - 密钥
	 * @param encoding
	 *            - 字符串编码方式
	 * @return - 签名结果，hex字符串
	 */
	public static String hmacSHASign(String aValue, String aKey, String encoding) {
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes(encoding);
			value = aValue.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}
		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			logger.info("发生异常" + e);
			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 20);
		dg = md.digest();
		return toHex(dg);
	}

	/**
	 * 对报文进行SHA签名
	 * 
	 * @param aValue
	 *            - 待签名的字符串（编码：UTF-8)
	 * @return - 签名结果，hex字符串
	 */
	public static String digest(String aValue) {
		return digest(aValue, ENCODE);

	}

	/**
	 * 对报文进行SHA签名
	 * 
	 * @param aValue
	 *            - 待签名的字符串
	 * @param encoding
	 *            - 字符串编码方式
	 * @return - 签名结果，hex字符串
	 */
	public static String digest(String aValue, String encoding) {
		aValue = aValue.trim();
		byte value[];
		try {
			value = aValue.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			value = aValue.getBytes();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			logger.info("发生异常" + e);
			return null;
		}
		return toHex(md.digest(value));
	}

	/**
	 * 对字符串进行签名
	 * 
	 * @param aValue
	 *            - 待签名字符串
	 * @param alg
	 *            - 签名算法名称（如SHA, MD5等）
	 * @param encoding
	 *            - 字符串编码方式
	 * @return - 签名结果，hex字符串
	 */
	public static String digest(String aValue, String alg, String encoding) {
		aValue = aValue.trim();
		byte value[];
		try {
			value = aValue.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			value = aValue.getBytes();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(alg);
		} catch (NoSuchAlgorithmException e) {
			logger.info("发生异常" + e);
			return null;
		}
		return toHex(md.digest(value));
	}

	/**
	 * 将字节转换为十六进制
	 * @param input
	 * @return
	 */
	public static String toHex(byte input[]) {
		if (input == null)
			return null;
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}
}
