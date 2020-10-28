package com.im.util;

import com.im.entity.ServerProp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author shiyakun
 */
public class PropUtils {

    public static ServerProp getProp() {
        ServerProp serverProp = new ServerProp();
        try {
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("server.properties");
            Properties properties = new Properties();

            properties.load(resourceAsStream);
            Integer port = Integer.valueOf(properties.get("server.port").toString());
            String host = properties.get("server.host").toString();
            serverProp.setHost(host);
            serverProp.setPort(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
       return serverProp;
    }
}
