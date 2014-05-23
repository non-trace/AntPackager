package com.nontrace.ant.timer;

import java.util.Date;
import java.util.TimerTask;

public class TamerTask extends TimerTask {
	private TimerConfig config;
	private static boolean isRunning = false;

	public static boolean isRunning() {
		return isRunning;
	}

	@SuppressWarnings("static-access")
	public TamerTask(TimerConfig config) {
		this.config = config;
	}

	public void run() {
		System.out.println("开始执行定任务");
		if (!config.isStart()) {
			System.out.println("定时任务由于配置文件中配置为未启用而停止");
			return;
		}
		executeLogic();
		System.out.println("定时任务结束");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void executeLogic() {
		if (!isRunning) {
			isRunning = true;
			try {
				Class clazz = Class.forName(config.getLogicClass());
				clazz.getMethod(config.getLogicMethod()).invoke(clazz.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
			isRunning = false;
		} else {
		}
	}

	/**
	 *  获取第一次执行定时器的时间 以及循环时间间隔
	 * 
	 * @return
	 */
	public Date initTimes() {
		return config.getRunTime();
	}

	public long getPeriod() {
		long period = config.getDate() * 24 * 60 * 60 * 1000 + config.getHour() * 60 * 60 * 1000 + config.getMinute() * 60 * 1000 + config.getSecond() * 1000;
		return period;
	}
}
