package com.hhly.ticket.base.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;

import com.hhly.ticket.util.PropertyUtil;


/**
 * @desc 线程池管理类
 * @author jiangwei
 * @date 2017年2月24日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class ThreadPoolManager {

	/**
	 * 定时任务线程池，参数corePoolSize 核心线程数（同时能执行的任务数为corePoolSize）
	 */
	private static final ScheduledThreadPoolExecutor SCHEDULED_EXECUTOR;
	
	private static final ThreadPoolExecutor THREAD_POOL;
	
	static {
		int corePoolSize = Integer.parseInt(PropertyUtil.getPropertyValue("application.properties", "manager_core_pool_size"));
		int maxCorePoolSize = Integer.parseInt(PropertyUtil.getPropertyValue("application.properties", "manager_max_core_pool_size"));
		int scheduledCorePoolSize = Integer.parseInt(PropertyUtil.getPropertyValue("application.properties", "manager_scheduled_core_pool_size"));
		SCHEDULED_EXECUTOR = new ScheduledThreadPoolExecutor(scheduledCorePoolSize);
		THREAD_POOL = new ThreadPoolExecutor(corePoolSize, maxCorePoolSize, 60, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(100),new ThreadPoolExecutor.CallerRunsPolicy());
	}

	/**
	 * 定时任务
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年2月27日 下午12:31:48
	 * @param remark
	 *            备注
	 * @param runnable
	 *            任务
	 * @param initialDelay
	 *            延迟时间
	 * @param period
	 *            执行周期
	 * @param unit
	 *            时间单位
	 * @return
	 */
	public static ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long initialDelay, long period,
			TimeUnit unit) {
		ScheduledFuture<?> future = SCHEDULED_EXECUTOR.scheduleAtFixedRate(runnable, initialDelay, period, unit);
		return future;
	}
	/**
	 * 添加延迟任务
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月15日 下午5:53:37
	 * @param runnable
	 * @param delay
	 * @param unit
	 * @return
	 */
	public static ScheduledFuture<?> schedule(Runnable runnable, long delay, TimeUnit unit) {
		return SCHEDULED_EXECUTOR.schedule(runnable, delay, unit);
	}
	/**
	 * 添加任务
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年5月16日 上午10:55:02
	 * @param runnable
	 */
	public static void submit(Runnable runnable){
		 THREAD_POOL.submit(runnable);
	}
}
