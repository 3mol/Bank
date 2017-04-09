package com.example.bank;

import java.util.Date;

/**
 * Created by 胡宇靖 on 2017/4/8 0008.
 */

public class User {
    int id;
    String name;
    float cash;
    long phone;
    String email;
    char active;
    Date datatime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCash() {
        return cash;
    }

    public void setCash(float cash) {
        this.cash = cash;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char isActive() {
        return active;
    }

    public void setActive(char active) {
        this.active = active;
    }

    public Date getDatatime() {
        return datatime;
    }

    public void setDatatime(Date datatime) {
        this.datatime = datatime;
    }

    @Override
    public String toString() {
        return "OK!";
    }
}
