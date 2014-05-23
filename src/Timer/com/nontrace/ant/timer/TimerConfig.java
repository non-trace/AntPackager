package com.nontrace.ant.timer;

import java.util.Date;

/**
 * 项目名称：CRMBpsManager
 * 类名称：Timer
 * 类描�?
 * 创建人：姚洪�?  
 * 创建时间�?014-3-17 下午2:46:48   
 * 修改人：姚洪�? 
 * 修改时间�?014-3-17 下午2:46:48    
 * 修改备注�?  
 * @version
 */

public class TimerConfig{
	private String logicClass;
	private String logicMethod;
	private boolean start;
	private Date runTime;
	private int date;
	private int hour;
	private int minute;
	private int second;
	private String description;
	/**
	 * @return the logicClass
	 */
	public String getLogicClass() {
		return logicClass;
	}
	/**
	 * @param logicClass the logicClass to set
	 */
	public void setLogicClass(String logicClass) {
		this.logicClass = logicClass;
	}
	/**
	 * @return the logicMethod
	 */
	public String getLogicMethod() {
		return logicMethod;
	}
	/**
	 * @param logicMethod the logicMethod to set
	 */
	public void setLogicMethod(String logicMethod) {
		this.logicMethod = logicMethod;
	}
	/**
	 * @return the start
	 */
	public boolean isStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(boolean start) {
		this.start = start;
	}
	/**
	 * @return the runTime
	 */
	public Date getRunTime() {
		return runTime;
	}
	/**
	 * @param runTime the runTime to set
	 */
	public void setRunTime(Date runTime) {
		this.runTime = runTime;
	}
	/**
	 * @return the date
	 */
	public int getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(int date) {
		this.date = date;
	}
	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}
	/**
	 * @param hour the hour to set
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}
	/**
	 * @return the minute
	 */
	public int getMinute() {
		return minute;
	}
	/**
	 * @param minute the minute to set
	 */
	public void setMinute(int minute) {
		this.minute = minute;
	}
	/**
	 * @return the second
	 */
	public int getSecond() {
		return second;
	}
	/**
	 * @param second the second to set
	 */
	public void setSecond(int second) {
		this.second = second;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
