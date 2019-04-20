package com.xieweihao.message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xieweihao.utils.DataUtil;
import com.xieweihao.utils.HttpUtils;
import com.xieweihao.utils.MD5Util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SmsSDK {
	private String apiUrl;
	private String SEND_URL;
	private String account;
	private String token;
	private String appId;
	private DataUtil dataUtil = DataUtil.getIstance();
	private String currentData = dataUtil.GetCurrentDate();

	/**
	 *
	 * @param apiUrl
	 */
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	/**
	 *
	 * @param accountSid
	 */
	public void setAccountSid(String accountSid) {
		this.account = accountSid;
	}

	/**
	 *
	 * @param accountToken
	 */
	public void setAuthToken(String accountToken) {
		this.token = accountToken;
	}

	/**
	 * @param appId
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * 发送短信
	 * 
	 * @param to
	 * @param templateId
	 * @param datas
	 * @return
	 */
	public Map<String, Object> sendSMS(String to, String templateId, String[] datas) {
		SMSResult smsResult = SMSResult.getIstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appId", appId);
		param.put("to", to);
		param.put("templateId", templateId);
		param.put("datas", datas);

		String result = null;
		if (isEmpty(apiUrl) || isEmpty(account) || isEmpty(token) || isEmpty(appId) || isEmpty(to)
				|| isEmpty(templateId)) {
			if (isEmpty(apiUrl)) {
				smsResult.setStatusCode("000019");
				smsResult.setStatusMsg("【短信】url空");
			} else if (isEmpty(account)) {
				smsResult.setStatusCode("000017");
				smsResult.setStatusMsg("【账号】帐号空");
			} else if (isEmpty(token)) {
				smsResult.setStatusCode("000018");
				smsResult.setStatusMsg("【账号】帐号令牌空");
			} else if (isEmpty(appId)) {
				smsResult.setStatusCode("000016");
				smsResult.setStatusMsg("【应用】应用ID为空");
			} else if (isEmpty(to)) {
				smsResult.setStatusCode("000006");
				smsResult.setStatusMsg("【短信】接收短信的手机号码为空");
			} else if (isEmpty(templateId)) {
				smsResult.setStatusCode("000008");
				smsResult.setStatusMsg("【短信】短信模板为空或未设置");
			}
		} else {
			HttpUtils instance = HttpUtils.getInstance();
			MD5Util md5Util = MD5Util.getInstance();
			SEND_URL = new StringBuffer().append(apiUrl).append("API/sendMessage").append("?sig=")
					.append(md5Util.md5(account + token + currentData)).toString();
			System.out.println(SEND_URL);
			result = instance.sendPostMessage(param, SEND_URL, account, currentData);
			
			if(isEmpty(result)){
				smsResult.setStatusCode("000010");
				smsResult.setStatusMsg("【服务器】服务器请求出错，请确认参数是否正确，如无法解决，请联系工作人员");
			}
		}
		Gson gson = new Gson();	
		if (!isEmpty(result))
			return gson.fromJson(result, new TypeToken<HashMap<String, Object>>() {
			}.getType());
		else
			return gson.fromJson(gson.toJson(smsResult), new TypeToken<HashMap<String, Object>>() {
			}.getType());
		// return param;
	}

	/*public Map<String, Object> sendMassSMS(String[] to, String templateId, String[] datas) {
		smsResult smsResult = smsResult.getIstance();
		Map<String, Object> param = new HashMap<String, Object>();

		if (isEmpty(apiUrl) || isEmpty(account) || isEmpty(token) || isEmpty(appId) || isEmpty(to)
				|| isEmpty(templateId)) {
			if (isEmpty(apiUrl)) {
				smsResult.setCode("000019");
				smsResult.setMessage("【短信】url空");
			} else if (isEmpty(account)) {
				smsResult.setCode("000017");
				smsResult.setMessage("【账号】帐号空");
			} else if (isEmpty(token)) {
				smsResult.setCode("000018");
				smsResult.setMessage("【账号】帐号令牌空");
			} else if (isEmpty(appId)) {
				smsResult.setCode("000016");
				smsResult.setMessage("【应用】应用ID为空");
			} else if (isEmpty(to)) {
				smsResult.setCode("000006");
				smsResult.setMessage("【短信】接收短信的手机号码为空");
			} else if (isEmpty(templateId)) {
				smsResult.setCode("000008");
				smsResult.setMessage("【短信】短信模板为空或未设置");
			}
		} else {
			param.put("appId", appId);
			String to_str = to.toString();
			param.put("to", to_str.subSequence(1, to.length - 1));
			param.put("templateId", templateId);
			param.put("datas", datas);
			HttpUtils instance = HttpUtils.getInstance();
			String result = null;
			MD5Util md5Util = MD5Util.getInstance();
			SEND_URL = new StringBuffer().append("http://").append(apiUrl).append("/API/sendMessage").append("?sig=")
					.append(md5Util.md5(account + token + currentData)).toString();
			System.out.println(SEND_URL);
			result = instance.sendPostMessage(param, SEND_URL, account, currentData);
			System.out.println(result);
			Gson gson = new Gson();
			if (null != result && result.length() > 0)
				smsResult = gson.fromJson(result, SMSResult.class);
			else {
				smsResult.setCode("000010");
				smsResult.setMessage("【服务器】服务器请求出错，请确认参数是否正确，如无法解决，请联系工作人员");
			}
		}
		param.clear();
		if ("000000".equals(smsResult.getCode())) {
			Map<String, Object> templateSMS = new HashMap<String, Object>();
			templateSMS.put("dateCreated", ((Map<String, Object>) (smsResult.getObject())).get("smsMessageSid"));
			templateSMS.put("smsMessageSid", ((Map<String, Object>) (smsResult.getObject())).get("dateCreated"));
			param.put("data", templateSMS);
			param.put("statusCode", "000000");
		} else {
			// ReadProperties readProperties = ReadProperties.getIstance();
			// String errorCode = smsResult.getCode();
			param.put("statusCode", smsResult.getCode());
			param.put("statusMsg", smsResult.getMessage());
		}
		return param;
	}*/

	private boolean isEmpty(String str) {
		return (("".equals(str)) || (str == null));
	}

	private boolean isEmpty(String[] strs) {
		return strs == null || strs.length == 0;
	}

	// 随机生成一个N位数注册码
	public static String productRandom(int n) {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < n; i++) {
			result += random.nextInt(10);
		}
		return result;
	}
}

