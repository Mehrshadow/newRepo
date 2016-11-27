package com.example.fcc.myapplication;

/**
 * Created by FCC on 11/12/2016.
 */

public class LoginData {

    private String loginName, loginDescription, customerId;

    public LoginData(String loginName, String loginDescription, String customerId) {
        this.loginName = loginName;
        this.loginDescription = loginDescription;
        this.customerId = customerId;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public String getLoginDescription() {
        return this.loginDescription;
    }

    public String getCustomerId() {
        return this.customerId;
    }
}
