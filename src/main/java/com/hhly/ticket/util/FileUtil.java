package com.hhly.ticket.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class FileUtil {
	/**
	 * 转换流为字符串
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年9月4日 上午10:21:12
	 * @param in
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static String getString(InputStream in) throws IOException {
		StringBuffer info = new StringBuffer();
		BufferedInputStream buf = new BufferedInputStream(in);
		byte[] buffer = new byte[1024];
		int iRead;
		while ((iRead = buf.read(buffer)) != -1) {
			info.append(new String(buffer, 0, iRead, "UTF-8"));
		}
		return info.toString();
	}
}
