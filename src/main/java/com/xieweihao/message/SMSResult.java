package com.xieweihao.message;

public class SMSResult {
	private String statusCode;
	private String statusMsg;
	private TemplateSMS templateSMS;
	
	private static volatile SMSResult instance;  
    
    private SMSResult() { 
    }   
    public static SMSResult getIstance() { 
        if (instance == null) {
            synchronized (SMSResult.class) {
                if (instance == null) {
                    instance = new SMSResult();   
                }   
            }   
        }   
        return instance;   
    }
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public TemplateSMS getTemplateSMS() {
		return templateSMS;
	}
	public void setTemplateSMS(TemplateSMS templateSMS) {
		this.templateSMS = templateSMS;
	}
	
}

