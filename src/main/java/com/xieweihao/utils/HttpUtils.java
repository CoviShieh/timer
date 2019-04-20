package com.xieweihao.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;



import com.google.gson.Gson;

public class HttpUtils {
	private static final String ENCODING_UTF_8 = "UTF-8";
	private static Gson gson =new Gson();
	private static volatile HttpUtils instance;  
	private HttpUtils() { 
		
	}   
	//取得HttpUtils的单例
	public static HttpUtils getInstance() { 
	    if (instance == null) {
	        synchronized (HttpUtils.class) {
	            if (instance == null) {
	                instance = new HttpUtils();   
	            }   
	        }   
	    }   
	    return instance;   
	}
	
	
	/**
	 * @param param  发送短信得参数 以map传递
	 * @param pathUrl 路径 
	 * @param currentData 当前时间
	 * @param account 账户Id
	 * @return
	 */
	public String sendPostMessage(Map<String, Object> params,String pathUrl, String account, String currentData) {
		
		MD5Util md5Util = MD5Util.getInstance();
		String authorization = md5Util.base64Encode(account+":"+currentData);
		System.out.println(authorization);
		try {
			URL url = new URL(pathUrl);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(3000);
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoInput(true);// 表示从服务器获取数据
			urlConnection.setDoOutput(true);// 表示向服务器写数据
			String requestString = gson.toJson(params);
			byte[] mydata = requestString.getBytes();
			// 表示设置请求体的类型是文本类型
			urlConnection.setRequestProperty("Content-Type","application/json;charset=utf-8");
			urlConnection.setRequestProperty("Content-Length", String.valueOf(mydata.length));
			urlConnection.setRequestProperty("Authorization", authorization);
			// 获得输出流,向服务器输出数据
			OutputStream outputStream = urlConnection.getOutputStream();
			outputStream.write(mydata, 0, mydata.length);
			outputStream.close();
			// 获得服务器响应的结果和状态码
			int responseCode = urlConnection.getResponseCode();
			if (responseCode == 200) {
				return changeInputStream(urlConnection.getInputStream());
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException");
		} catch (IOException e) {
			System.out.println("IOException");
		}
		return "";
	}
	/**
	 * 将输入流转化为字符串
	 * @param inputStream 
	 * @return
	 */
	private String changeInputStream(InputStream inputStream) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = "";
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					outputStream.write(data, 0, len);
				}
				result = new String(outputStream.toByteArray(), ENCODING_UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	
	/**
	 * @param pathUrl 路径 
	 * @param currentData 当前时间
	 * @param account 账户Id
	 * @return
	 */
	public String sendPostVoice(String pathUrl, String accountSid, String data) {
		
		MD5Util md5Util = MD5Util.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhh");
		String authorization = md5Util.base64Encode(accountSid+":"+new Date());
		System.out.println(authorization);
		try {
			URL url = new URL(pathUrl);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(3000);
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoInput(true);// 表示从服务器获取数据
			urlConnection.setDoOutput(true);// 表示向服务器写数据
			String requestString = gson.toJson("");
			byte[] mydata = requestString.getBytes();
			// 表示设置请求体的类型是文本类型
			urlConnection.setRequestProperty("Content-Type","application/json;charset=utf-8");
			urlConnection.setRequestProperty("Content-Length", String.valueOf(mydata.length));
			urlConnection.setRequestProperty("Authorization", authorization);
			// 获得输出流,向服务器输出数据
			OutputStream outputStream = urlConnection.getOutputStream();
			outputStream.write(mydata, 0, mydata.length);
			outputStream.close();
			// 获得服务器响应的结果和状态码
			int responseCode = urlConnection.getResponseCode();
			if (responseCode == 200) {
				return changeInputStream(urlConnection.getInputStream());
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException");
		} catch (IOException e) {
			System.out.println("IOException");
		}
		return "";
	}
	
}

