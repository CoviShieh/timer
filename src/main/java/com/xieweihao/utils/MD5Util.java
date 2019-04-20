package com.xieweihao.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Base64;

public class MD5Util {
	private static volatile MD5Util instance;

	private MD5Util() {
	}

	// 取得MD5Util的单例
	public static MD5Util getInstance() {
		if (instance == null) {
			synchronized (MD5Util.class) {
				if (instance == null) {
					instance = new MD5Util();
				}
			}
		}
		return instance;
	}

	/***
	 * MD5加码 生成32位md5码
	 */
	public String md5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString().toUpperCase();
	}

	/**
	 * base64编码
	 * 
	 * @param code
	 * @return
	 */
	public String base64Encode(String code) {
		try {
			return Base64.getEncoder().encodeToString(code.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * base64解码
	 * 
	 * @param code
	 * @return
	 */
	public String base64Decode(String code) {
		try {
			byte[] asBytes = Base64.getDecoder().decode(code);
			return new String(asBytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
