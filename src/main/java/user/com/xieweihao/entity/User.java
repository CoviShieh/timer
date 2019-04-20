package com.xieweihao.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user")
public class User implements Serializable {

    private Integer userId;

    @NotNull(message = "昵称不能为空")
    @Size(min = 2, max = 18, message = "昵称最低2位、最高18位")
    private String userNickName = "";

    @NotNull(message = "手机号码不能为空")
    @Size(min = 11, max = 11, message = "手机号码只能是11位")
    private String mobileNo = "";

    @NotNull(message = "密码不能为空")
    @Size(min = 6, max = 18, message = "密码只能是6到18位")
    private String password = "";

    private Date registerTime = new Date();

    private Integer validity = 0;

    private String email = "";

    private String headPortrait = "";


    /**
     * 用于自动登陆时的凭证
     */
    private String loginToken = "";
    

    /* 主键*/
    @Id
    @GeneratedValue
    public Integer getUserId() {
        return userId;
    }


    public User() {

    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userNickName='" + userNickName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", password='" + password + '\'' +
                ", registerTime=" + registerTime +
                ", validity=" + validity +
                ", email='" + email + '\'' +
                ", headPortrait='" + headPortrait + '\'' +
                '}';
    }
}

