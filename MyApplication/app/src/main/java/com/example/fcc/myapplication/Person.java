package com.example.fcc.myapplication;

/**
 * Created by FCC on 11/13/2016.
 */

public class Person {

    private String personId, customerId, name, des, avatar, serverIP, mobilePort, exKey;

    public Person(String personId, String customerId, String name, String des, String avatar, String serverIP, String mobilePort, String exKey) {
        this.personId = personId;
        this.customerId = customerId;
        this.name = name;
        this.des = des;
        this.avatar = avatar;
        this.serverIP = serverIP;
        this.mobilePort = mobilePort;
        this.exKey = exKey;
    }

    public String getPersonId() {
        return personId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getDes() {
        return des;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getServerIP() {
        return serverIP;
    }

    public String getMobilePort() {
        return mobilePort;
    }

    public String getExKey() {
        return exKey;
    }
}
