package com.example.entity;

import java.io.Serializable;

/**
 * @author shiyakun
 * @Description TODO
 */
public class Register implements Serializable {
    private Integer userId;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
