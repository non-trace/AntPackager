package com.jtcrm.ant.timer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.cthq.crm.project.common.util.ReadFile;
import com.cthq.crm.project.common.xml.imp.XMLCommonReader;
import com.cthq.crm.project.common.xml.imp.XMLElement;
import com.cthq.crm.project.common.xml.imp.XMLNodeCollection;
import com.cthq.crm.project.common.xml.imp.XMLNodeCollection.Entry;

public class TimerTaskListener implements ServletContextListener {
	private Timer timer;

	public void contextInitialized(ServletContextEvent sce) {
		ReadFile rf = new ReadFile();
		String xmlContext = rf.getFileContent(sce.getServletContext().getRealPath("/WEB-INF/classes/timerTaskConfig.xml"),"UTF-8");
		List<String> xmlPahts = new ArrayList<String>();
		xmlPahts.add("tasks");
		xmlPahts.add("tasks/task");
		XMLCommonReader reader = new XMLCommonReader();
		reader.setXMLLevelList(xmlPahts);
		try {
			reader.analysisXML(xmlContext);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int count = reader.xmlDomElementCount("tasks/task");
		for (int i = 0; i < count; i++) {
			XMLElement element  = reader.xmlDomElement("tasks/task", i);
			if (element!=null) {
				XMLNodeCollection nodes=element.getElementLeafNodeColl();
				if (nodes!=null) {
					TimerConfig config = new TimerConfig();
					Iterator iter = nodes.ListIterator();
					while(iter.hasNext()) {
						Entry entry = (Entry)iter.next();
						String entryName = entry.xmlNode.getName();
						String entryValue = entry.xmlNode.getValue();
						if ("logicClass".equals(entryName)) {
							config.setLogicClass(entryValue);
						}else if("logicMethod".equals(entryName)) {
							config.setLogicMethod(entryValue);
						}else if("switch".equals(entryName)) {
							config.setStart("1".equals(entryValue)?true:false);
						}else if("runTime".equals(entryName)) {
							if (entryValue!=null&&!"".equals(entryValue)) {
								DateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								try {
									config.setRunTime(sd.parse(entryValue));
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}
						}else if("day".equals(entryName)) {
							config.setDate(Integer.parseInt(entryValue));
						}else if("hour".equals(entryName)) {
							config.setHour(Integer.parseInt(entryValue));
						}else if("minute".equals(entryName)) {
							config.setMinute(Integer.parseInt(entryValue));
						}else if("second".equals(entryName)) {
							config.setSecond(Integer.parseInt(entryValue));
						}else if("description".equals(entryName)) {
							config.setDescription(entryValue);
						}
					}
					timer = new Timer();
					TamerTask tt = new TamerTask(config);
					Date date = tt.initTimes();
					System.out.println("启动定时器：" + config.getDescription());
					if (null == date) {
						System.out.println("启动定时器:\""+config.getDescription()+"\" 第一次执行时间一分钟后，执行周期" + tt.getPeriod()+"ms");
						timer.schedule(tt,60 * 1000, tt.getPeriod());
					} else {
						Calendar now = Calendar.getInstance();
						Calendar cdate = Calendar.getInstance();
						now.setTime(new Date());
						cdate.setTime(date);
						if (cdate.before(now)) {//如果设置的启动时间比当前时间早，将启动时间的年月日改为当前时间的年月日
							cdate.set(Calendar.YEAR, now.get(Calendar.YEAR));
							cdate.set(Calendar.MONDAY, now.get(Calendar.MONDAY));
							cdate.set(Calendar.DATE, now.get(Calendar.DATE));
							if (cdate.before(now)) {//如果还是早，设置为明天的这个时间点
								cdate.add(Calendar.DATE,1);
							}
						}
						date = cdate.getTime();
						System.out.println("启动定时器:\""+config.getDescription()+"\" 第一次执行时间" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) + "，执行周期" + tt.getPeriod()+"ms");
						timer.schedule(tt, date, tt.getPeriod());
					}
				}
			}
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
		if (null != timer) {
			timer.cancel();
		}
	}
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sd.parse("2013-02-11 07:30:24");
		Calendar now = Calendar.getInstance();
		Calendar cdate = Calendar.getInstance();
		now.setTime(new Date());
		cdate.setTime(date);
		if (cdate.before(now)) {//如果设置的启动时间比当前时间早，将启动时间的年月日改为当前时间的年月日
			cdate.set(Calendar.YEAR, now.get(Calendar.YEAR));
			cdate.set(Calendar.MONDAY, now.get(Calendar.MONDAY));
			cdate.set(Calendar.DATE, now.get(Calendar.DATE));
			if (cdate.before(now)) {//如果还是早，设置为明天的这个时间点
				cdate.add(Calendar.DATE,1);
			}
		}
		date = cdate.getTime();
		System.out.println(date);
	}
}
