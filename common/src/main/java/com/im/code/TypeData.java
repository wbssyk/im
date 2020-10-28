package com.im.code;

/**
 * @author shiyakun
 * @Description TODO
 */
public interface TypeData {

    /**
     * 用户注册信息状态
     */
    int ACK = 0;

    /**
     * ping消息状态
     */
    int PING = 1;

    /**
     * pong消息状态
     */
    int PONG = 2;
    /**
     * 发送消息状态
     */
    int MESSAGE = 3;


}
