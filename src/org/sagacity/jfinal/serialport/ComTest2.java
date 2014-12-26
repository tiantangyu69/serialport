package org.sagacity.jfinal.serialport;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

public class ComTest2 {
	/**
	 * @param args
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

		String port = "COM3";

		for (Enumeration<CommPortIdentifier> e = CommPortIdentifier
				.getPortIdentifiers(); e.hasMoreElements();) {

			CommPortIdentifier portId = e.nextElement();

			if (portId.getName().equals(port)) {

				System.out.println("\r");

				System.out.println("找到端口： " + port);

				sendAtTest(portId);

			}

		}

	}

	private static void sendAtTest(CommPortIdentifier portId)

	throws PortInUseException, UnsupportedCommOperationException,

	TooManyListenersException, IOException, InterruptedException {

		System.out.println("打开端口 …");

		final SerialPort serialPort = (SerialPort) portId.open("wavecom", 100);

		serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);

		serialPort.setSerialPortParams(115200,// 波特率

				SerialPort.DATABITS_8,// 数据位数

				SerialPort.STOPBITS_1, // 停止位

				SerialPort.PARITY_NONE);// 奇偶位

		System.out.println("端口已打开 …");

		OutputStream outputStream = serialPort.getOutputStream();

		outputStream.write("0xEE 0xCC 0x0A 0x01 0x01 00 00 0A 0B 0C 0D 0xFA 0xFB 0xFF"
				.getBytes());

		Thread.sleep(500);
		serialPort.close();

	}

}
