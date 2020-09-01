package com.example.client;

import com.example.client.base.BaseAbstractClient;
import com.example.entity.ServerProp;
import com.example.listener.MsgListener;

/**
 * @author shiyakun
 */
public class Client extends BaseAbstractClient {

    @Override
    public MsgListener getListener() {
        return new TestListener();
    }


    @Override
    public ServerProp getHostAndPort() {
        ServerProp serverProp = new ServerProp();
        serverProp.setPort(10000);
        serverProp.setHost("127.0.0.1");
        return serverProp;
    }


}
