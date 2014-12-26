/**
 * 
 */
package org.sagacity.jfinal.serialport;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Admin
 * 设置串口监听随着项目启动自启动
 */
public class SerialPortListener implements ServletContextListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ListeningDataThread thread = new ListeningDataThread();// 项目启动时调用监听数据的线程监听串口数据
		thread.start();// 启动监听线程
	}
}
