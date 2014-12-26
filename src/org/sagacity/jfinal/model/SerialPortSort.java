/**
 * 
 */
package org.sagacity.jfinal.model;

import java.util.Comparator;

/**
 * @author Admin
 * 串口数据排序类
 */
public class SerialPortSort implements Comparator<SerialPortData> {

	@Override
	public int compare(SerialPortData o1, SerialPortData o2) {
		return o1.getInt("id") > o2.getInt("id") ? 1 : -1;
	}

}
