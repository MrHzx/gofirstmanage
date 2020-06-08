package com.gofirst.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	/**
	 * 	在当前时间上加天数的方法
	 * @param date 当前时间
	 * @param num 天数
	 * @return 需要获取的时间
	 */
	public static Date getNextDay(Date date,int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +num);// +1今天的时间加一天
		date = calendar.getTime();
		return date;
	}
}
