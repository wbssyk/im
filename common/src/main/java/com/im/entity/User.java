package com.im.entity;

import java.io.Serializable;

/**
 * @author shiyakun
 */


public class User implements Serializable {
    private String username;
    private int id;
    private String sex;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }
}


