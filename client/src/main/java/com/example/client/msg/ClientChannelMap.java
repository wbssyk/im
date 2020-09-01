package com.example.client.msg;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shiyakun
 * @Description TODO
 */
public class ClientChannelMap {
    private static Map<Integer, Channel> map = new ConcurrentHashMap<Integer, Channel>();

    public static void add(Integer fromId, Channel channel) {
        map.put(fromId, channel);
    }

    public static Channel get(Integer fromId) {
        return map.get(fromId);
    }


    public static Integer getFromId(Channel channel) {
        Integer fromId = -1;
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getValue() == channel) {
                fromId = (Integer) entry.getKey();
                break;
            }
        }
        return fromId;
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
