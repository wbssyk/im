package com.im;

import com.im.client.base.BaseAbstractClient;
import com.im.code.TypeData;
import com.im.entity.*;
import com.im.listener.MsgListener;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author shiyakun
 */
public class Client extends BaseAbstractClient implements MsgListener {

    @Override
    public MsgListener getListener() {
        return this;
    }


    @Override
    public ServerProp getHostAndPort() {
        ServerProp serverProp = new ServerProp();
        serverProp.setPort(10000);
        serverProp.setHost("127.0.0.1");
        return serverProp;
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
        //说明是开始注册
        //0:1
        //用户唯一标识
       new ArrayList<String>().stream().map(String::length).forEach(t->{
           System.out.println(t);
       });

        Scanner sc = new Scanner(System.in);
        while (true) {
            //获取一个键盘扫描器
            String nextLine = sc.nextLine();
            if (StringUtils.isNotEmpty(nextLine)) {
                Message message = new Message();
                String[] split = nextLine.split(":");
                if (split == null || split.length == 0) {
                    break;
                }
                if (Integer.valueOf(split[0]) == 0) {
                    Register register = new Register();
                    try {
                        //说明是开始注册
                        //0:1
                        //用户唯一标识
                        register.setUserId(Integer.valueOf(split[1]));
                        client.register(register);
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                } else {
                    //正常消息
                    // 第一个是消息类型,第二个是发送方id,第三个是接收方id,第四个是消息  如3:1:2:我是一个正常消息
                    //发送方
                    message.setFromId(Integer.valueOf(split[1]));
                    //接收方
                    message.setToId(Integer.valueOf(split[2]));
                    message.setBody(split[3]);
                    client.sendMessage(message);
                }
            }
        }
    }


    @Override
    public void receiveMsg(Message message) {
        //1：在这里做进一步数据处理
        System.out.println(message);
    }
}
