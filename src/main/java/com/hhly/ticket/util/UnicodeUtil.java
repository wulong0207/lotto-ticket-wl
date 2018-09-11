package com.hhly.ticket.util;

import java.io.UnsupportedEncodingException;
/**
 * @desc 编码装换工具
 * @author jiangwei
 * @date 2017年8月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class UnicodeUtil {
	 /**
	  * utf 转码 gbk
	  * @author jiangwei
	  * @Version 1.0
	  * @CreatDate 2017年8月14日 下午4:38:47
	  * @param t
	  * @return
	  */
     public static String unicode(String t,String beforeCharset,String afterCharset){
    	String result = null;
		try {
			 String before = new String(t.getBytes(beforeCharset));
			 String unicode = new String(before.getBytes(),beforeCharset);
			 result = new String(unicode.getBytes(afterCharset));  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
     }
     
     public static void main(String[] args) {
    	 System.out.println(unicode("�ɹ���ϵͳ������", "GBK", "utf-8"));
	 }
}
