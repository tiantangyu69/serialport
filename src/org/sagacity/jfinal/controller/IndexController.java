package org.sagacity.jfinal.controller;

import gnu.io.SerialPort;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

import org.sagacity.jfinal.model.SerialPortData;
import org.sagacity.jfinal.model.SerialPortSort;
import org.sagacity.jfinal.serialport.ListeningDataThread;

import com.jfinal.core.Controller;

/**
 * IndexController
 * 系统首页控制器
 */
public class IndexController extends Controller {
	/**
	 * 系统首页
	 */
	public void index() {
		String startDate = getPara("startDate");// 获取查询开始时间
		String endDate = getPara("endDate");// 获取查询结束时间
		if (null != startDate && !"".equals(startDate.trim())
				&& null != endDate && !"".equals(endDate.trim())) {// 查询时间都不为空时调用条件查询
			List<SerialPortData> list = SerialPortData.instance.findByCondition(startDate, endDate);// 获取时间范围内容的温度和湿度列表数据
			List<SerialPortData> list2 = SerialPortData.instance.findByCondition(startDate, endDate);
			setAttr("list", list);
			Collections.sort(list2, new SerialPortSort());
			setAttr("list2", list2);
			
			setAttr("startDate", startDate);
			setAttr("endDate", endDate);
		} else{// 查询条件为空时查询所有的数据列表
			List<SerialPortData> list = SerialPortData.instance.findAll();// 获取所有的温度和湿度列表数据
			List<SerialPortData> list2 = SerialPortData.instance.findAll();
			setAttr("list", list);
			Collections.sort(list2, new SerialPortSort());
			setAttr("list2", list2);
		}
		render("index.jsp");
	}
	
	/**
	 * 打开继电器
	 */
	public void openRelay(){
		SerialPort port = ListeningDataThread.SERIAL_PORT;
		try {
			OutputStream outputStream = port.getOutputStream();// 获取串口输出流
			outputStream.write("EE CC 00 00 00 79 6F 00 00 00 00 01 0B 0A 0F 01 01 00 00 00 00 00 01 00 00 FF"
					.getBytes());// 向串口中发送打开继电器的命令
			outputStream.close();// 关闭输出流
			renderText("打开继电器成功！");
		} catch (IOException e) {
			e.printStackTrace();
			renderText("打开继电器失败！");
		}
	}
	
	/**
	 * 关闭继电器
	 */
	public void closeRelay(){
		SerialPort port = ListeningDataThread.SERIAL_PORT;
		try {
			OutputStream outputStream = port.getOutputStream();// 获取串口输出流
			outputStream.write("EE CC 00 00 00 79 6F 00 00 00 00 01 0B 0A 0F 01 01 00 00 00 00 00 00 00 00 FF"
					.getBytes());// 向串口中发送关闭继电器的命令
			outputStream.close();// 关闭输出流
			renderText("关闭继电器成功！");
		} catch (IOException e) {
			e.printStackTrace();
			renderText("关闭继电器失败！");
		}
	}
}