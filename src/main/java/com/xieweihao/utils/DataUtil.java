package com.xieweihao.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {
private static volatile DataUtil instance;  
	private DataUtil() { 
	}   
	public static DataUtil getIstance() { 
	    if (instance == null) {
	        synchronized (DataUtil.class) {
	            if (instance == null) {
	                instance = new DataUtil();   
	            }   
	        }   
	    }   
	    return instance;   
	}
	/**
	 * 获取系统当前
	 * @return
	 */
	public String GetCurrentDate(){
		Date date = new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String time=format.format(date);
		return time;
	}
}

