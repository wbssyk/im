package com.example;


import com.example.entity.Model;
import com.example.listener.MsgListener;

/**
 * @author shiyakun
 * @Description TODO
 */
public class TestListener implements MsgListener {



    @Override
    public void receiveMsg(Model model) {
        //1：在这里做进一步数据处理
        System.out.println(model);
    }
}
