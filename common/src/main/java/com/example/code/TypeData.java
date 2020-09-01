package com.example.code;

/**
 * @author shiyakun
 * @Description TODO
 */
public interface TypeData {

    /**
     * 用户注册信息状态
     */
    byte ACK = 0;

    /**
     * ping消息状态
     */
    byte PING = 1;

    /**
     * pong消息状态
     */
    byte PONG = 2;
    /**
     * 发送消息状态
     */
    byte MESSAGE = 3;


}
