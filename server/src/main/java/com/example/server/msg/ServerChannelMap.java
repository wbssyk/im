package com.example.server.msg;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shiyakun
 * @Description TODO
 */
public class ServerChannelMap {
    private static Map<Integer, Channel> map = new ConcurrentHashMap<Integer, Channel>();

    public static void add(Integer clientId, Channel channel) {
        map.put(clientId, channel);
    }

    public static Channel get(Integer clientId) {
        return map.get(clientId);
    }

    public static void remove(Channel channel) {
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getValue() == channel) {
                map.remove(entry.getKey());
            }
        }
    }

    public static boolean isEmpty() {
        if (map == null || map.size() == 0) {
            return true;
        }
        return false;
    }
}
