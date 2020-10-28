package com.im.listener;

import com.im.entity.Ack;
import com.im.entity.Message;

/**
 * @author shiyakun
 * @Description TODO
 */
public interface MsgListener {

    /**
     * 接受消息
     * @param message
     */
    void receiveMsg(Message message);
}
