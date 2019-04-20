package com.xieweihao.message;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class SendSmsDemo {


	
/*	public static void main(String[] args) {
		
		*//*
	     * 短信接口调用说明
	     * 假设您想给手机号159xxxx1234发送一条短信，
	     * 您打算使用的模板ID为2，对应模板内容为："您好，您的手机验证码为：{1}，{2}分钟内有效"
	     * 给这个模板传入的参数为5678和3，则调用方式为：
	     * sendSMS("159xxxx1234", "2", ['5678','3']);
	     * 最终收到的短信内容为："【云通讯】您好，您的手机验证码为5678，3分钟内有效"
	     *//*
		//SendSmsDemo.sendSMS("13719193845", "4", new String[]{"5937", "3"});
	}*/
	
	public static void sendSMS(String to, String tempId, String[] datas, HttpSession session) {
		// 初始化SDK
		SmsSDK smsSDK = new SmsSDK();
	    
		/*
		 * 帐户参数配置
		 * 用户登录之后在开发者控制台【首页】寻找以下配置参数：
		 * AccountSid: 帐号ID，对应开发者帐号下的 ACCOUNT SID
		 * AuthToken: 授权令牌，对应开发者帐号下的 AUTH TOKEN
		 * ApiUrl: API调用路径，对应着开发者后台中的API URL
		 * AppId: 应用ID，对应开发者控制台【应用管理】S中的某个APP ID
		 */
	    smsSDK.setAccountSid("564c1b5382a04667ab70de3405d770c7");
		smsSDK.setAuthToken("0a4dcf14ad984d2a85619e2bc2795c1b");
		smsSDK.setApiUrl("http://www.dahancloud.com/");
		smsSDK.setAppId("b35c99f9d0874bb9bf0fe56929ba14ce");

		/*
	     * 发送模板短信
	     * to 手机号码，多个手机号码需用英文逗号分开
	     * tempId 模板ID，对应开发者后台中的模板编号
	     * datas 替换内容，格式为数组，例如：new String[]{"1238", "3"}
	     */
		Map<String, Object> result = smsSDK.sendSMS(to, tempId, datas); //实际调用请保证这些参数真实有效！
		System.out.println(result);
		if ("000000".equals(result.get("statusCode"))) {
			// 发送成功
			Map<String, Object> templateSMS = (Map<String, Object>) result.get("templateSMS");
			System.out.println("dateCreated: " + templateSMS.get("dateCreated"));
			System.out.println("smsMessageSid: " + templateSMS.get("smsMessageSid"));
			
			//发送成功，将验证码存到session中
			session.setAttribute("validateCode", datas[0]);



		} else {
			//错误处理
			System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));


		}
	}
	
	
	

}

