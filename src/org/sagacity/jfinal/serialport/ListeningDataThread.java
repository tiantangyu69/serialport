/**
 * 
 */
package org.sagacity.jfinal.serialport;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TooManyListenersException;

import org.sagacity.jfinal.model.SerialPortData;

/**
 * @author Admin 监听串口传输的数据线程对象
 */
public class ListeningDataThread extends Thread {
	public static SerialPort SERIAL_PORT;

	@Override
	public void run() {
		String port = "COM2";// 连接的串口名称
		for (@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> e = CommPortIdentifier
				.getPortIdentifiers(); e.hasMoreElements();) {// 循环遍历操作系统中的所有串口

			CommPortIdentifier portId = e.nextElement();

			if (portId.getName().equals(port)) {// 找到COM2串口
				System.out.println("找到端口： " + port);
				try {
					sendAtTest(portId);// 连接串口并实时监听串口传入的数据
				} catch (PortInUseException | UnsupportedCommOperationException
						| TooManyListenersException | IOException
						| InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 测试串口是否可用并连接串口，监听串口发送的数据
	 * 
	 * @param portId
	 *            串口编号
	 * @throws PortInUseException
	 * @throws UnsupportedCommOperationException
	 * @throws TooManyListenersException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void sendAtTest(CommPortIdentifier portId)
			throws PortInUseException, UnsupportedCommOperationException,
			TooManyListenersException, IOException, InterruptedException {
		System.out.println("打开端口 …");
		final SerialPort serialPort = (SerialPort) portId.open("wavecom", 1000);
		serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
		serialPort.setSerialPortParams(115200,// 波特率
				SerialPort.DATABITS_8,// 数据位数
				SerialPort.STOPBITS_1, // 停止位
				SerialPort.PARITY_NONE);// 奇偶位
		System.out.println("串口打开成功：波特率【115200】，数据位【8】，停止位【1】，奇偶校验【无】");
		SERIAL_PORT = serialPort;
		while (true) {
			byte[] data = new byte[1024];
			InputStream inputStream = serialPort.getInputStream();// 获取串口的数据
			Thread.sleep(5000);
			int i = inputStream.read(data);
			if (i > 0) {
				System.out.println("成功收到指令返回值:" + new String(data, 0, i));
				Map<String, Object> params = new HashMap<String, Object>();// 将从串口接收的数据进行解析，生成温度和湿度并保存到数据库中
				params.put("temperature", 32.3);// 保存计算出的温度
				params.put("humidity", 47.5);// 保存计算出的湿度
				params.put("data", new String(data, 0, i));// 保存从串口接收的原始数据
				params.put("receiveTime",
						new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));// 设置数据保存时间
				new SerialPortData().setAttrs(params).save();
			}
		}
	}

}
