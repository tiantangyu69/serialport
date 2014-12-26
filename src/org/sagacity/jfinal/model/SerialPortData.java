/**
 * 
 */
package org.sagacity.jfinal.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

/**
 * @author Admin 串口数据的实体
 */
@SuppressWarnings("serial")
public class SerialPortData extends Model<SerialPortData> {
	public static final SerialPortData instance = new SerialPortData();

	/**
	 * 查找串口数据列表
	 * 
	 * @return
	 */
	public List<SerialPortData> findAll() {
		return find("select * from SerialPortData order by id desc");
	}

	public List<SerialPortData> findByCondition(String startDate, String endDate) {
		return find(
				"select * from SerialPortData where receiveTime between ? and ? order by id desc",
				startDate, endDate);
	}
}
