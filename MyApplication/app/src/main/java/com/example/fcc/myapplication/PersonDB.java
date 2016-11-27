package com.example.fcc.myapplication;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Person")
public class PersonDB extends Model {

    @Column(name = "PersonId")
    public String personId;

    @Column(name = "CustomerId")
    public String customerId;

    @Column(name = "Name")
    public String name;

    @Column(name = "Des")
    public String des;

    @Column(name = "Avatar")
    public String avatar;

    @Column(name = "ServerIP")
    public String serverIP;

    @Column(name = "MobilePort")
    public String mobilePort;

    @Column(name = "ExKey")
    public String exKey;


    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getMobilePort() {
        return mobilePort;
    }

    public void setMobilePort(String mobilePort) {
        this.mobilePort = mobilePort;
    }

    public String getExKey() {
        return exKey;
    }

    public void setExKey(String exKey) {
        this.exKey = exKey;
    }
}
