package com.hhly.ticket.util;

import java.util.ArrayList;
import java.util.List;
/**
 * @desc 集合工具类
 * @author jiangwei
 * @date 2017年6月5日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class CollectionUtil{
	/**
	 * list集合按照num大小进行拆分
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年6月5日 上午10:00:12
	 * @param list 总集合
	 * @param num 拆分大小
	 * @return
	 */
	public static <T> List<List<T>> subList(List<T> list,int num){
		List<List<T>> result = new ArrayList<>();
		if (list ==null || list.isEmpty()) {
			return result;
		}
		int times = list.size()/num +1;
		for (int i = 0; i < times; i++) {
			int start = i * num;
			int end = start + num;
			if((i + 1) == times){
				end = list.size();
				if(start == end){
					break;
				}
			}
			List<T> sub = list.subList(start, end);
			result.add(sub);
		}
		return result;
	}
}
