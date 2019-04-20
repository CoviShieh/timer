package com.xieweihao.enums;


/**
 * 定义返回对象的Code值
 */
public enum ResultEnum {
    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(1, "成功"),
    PARAM_ERROR(-2, "提交的参数错误"),
    CAPTCHA_ERROR(-3, "验证码错误"),
    LOGIN_ERROR(-4, "用户名/密码错误"),
    USER_NOEXIST(-5, "找不到用户/用户还没登陆"),
    REGISTER_ERROR(-6, "注册失败"),
    UPLOAD_USERINFO_ERROR(-7, "更新用户信息失败"),
    SEND_MOBILEMESSAGE_ERROR(-8, "发送短信失败"),

    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }



}
