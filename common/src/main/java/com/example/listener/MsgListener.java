package com.example.listener;

import com.example.entity.Model;

/**
 * @author shiyakun
 * @Description TODO
 */
public interface MsgListener {

    /**
     * 接受消息
     * @param model
     */
    void receiveMsg(Model model);
}
