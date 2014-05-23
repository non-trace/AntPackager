package com.nontrace.ant.task;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

/**
 * 类名称：AntPackageTask 类描述: 创建人：姚洪肖 创建时间：2014-4-14 上午10:31:01 修改人：姚洪肖
 * 修改时间：2014-4-14 上午10:31:01 修改备注：
 * 
 * @version
 */
public class AntPackageTask {
	
	public void runAnt() {
		String classPath = new File(AntPackageTask.class.getResource("").getPath()).getParent();
		classPath = classPath.substring(0,classPath.indexOf("classes"));
		Properties props = new Properties();
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(classPath + "classes/antProperties.properties"));
			props.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		File buildFile = new File(classPath + "ant/build.xml");
		// 创建一个ANT项目
		Project p = new Project();
		
		p.setProperty("svn.username",props.getProperty("svn.username"));
		p.setProperty("svn.password",props.getProperty("svn.password"));
		p.setProperty("svn.source",props.getProperty("svn.source"));
		p.setProperty("svn.temp",props.getProperty("svn.temp"));
		p.setProperty("svn.repository",props.getProperty("svn.repository"));
		p.setProperty("projectName",props.getProperty("projectName"));
		
		p.setProperty("svn.libPath",classPath + "svnlib");
		p.setProperty("dir.javaee",classPath + "javaee");
		// 创建一个默认的监听器,监听项目构建过程中的日志操作
		DefaultLogger consoleLogger = new DefaultLogger();
		consoleLogger.setErrorPrintStream(System.err);
		consoleLogger.setOutputPrintStream(System.out);
		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
		p.addBuildListener(consoleLogger);
		try {
			p.fireBuildStarted();
			// 初始化该项目
			p.init();
			ProjectHelper helper = ProjectHelper.getProjectHelper();
			// 解析项目的构建文件
			helper.parse(p, buildFile);
			
			String sourceDir = p.getProperty("svn.source") + "/" + p.getProperty("tempPro.today");
			File sourceDirFile = new File(sourceDir);
			if (sourceDirFile.exists()&&sourceDirFile.isDirectory()) {
				
			}else{
				sourceDirFile.mkdirs();
			}
			//如果还没有检出，先检出
			if (!new File(p.getProperty("svn.temp") + "/" + p.getProperty("projectName")).exists()) {
				p.executeTarget("svnCheckout");
			}
			
			// 更新代码，编译，并打包
			p.executeTarget(p.getDefaultTarget());
			p.fireBuildFinished(null);
		} catch (BuildException be) {
			p.fireBuildFinished(be);
			be.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String classPath = "/opt/IBM/WebSphere/AppServer/profiles/BPS/installedApps/localhostNode01Cell/AntPackager_war.ear/AntPackager.war/WEB-INF/classes/com/jtcrm/ant";
		classPath = classPath.substring(0,classPath.indexOf("classes"));
		System.out.println(classPath);
	}

	public void test() {
		System.out.println();
	}
}
