package com.example.client.msg;

import com.example.entity.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shiyakun
 * @Description 用户获取到的消息
 */
public class ClientReceiveMsg {

    /**
     * 存储消息的队列
     */
    private static Queue<Model> queue = new ArrayBlockingQueue(10);

    /**
     * 储存多个用户的消息队列
     */
    private static Map<Integer, Queue<Model>> map = new ConcurrentHashMap<>();

    /**
     * 存储消息
     *
     * @param fromId 发送发用户id
     * @param model  消息
     */
    public static void add(Integer fromId, Model model) {
        queue.add(model);
        map.put(fromId, queue);
        List<Model> models = get(fromId);
        models.forEach(t -> {
            System.out.println(t);
        });
    }

    /**
     * 获取消息
     *
     * @param fromId 发送发用户id
     * @return List<Model> 返回集合，消息为空返回空集合
     */
    public static List<Model> get(Integer fromId) {
        Queue<Model> queue = map.get(fromId);
        List<Model> result = new ArrayList<>();
        if (queue.size() == 0) {
            return result;
        }
        if (queue.size() == 1) {
            result.add(queue.poll());
            return result;
        }
        queue.forEach(t -> {
            result.add(t);
        });
        return result;
    }

    public static void remove(Integer fromId){
        map.remove(fromId);
    }

    public static boolean isEmpty() {
        if (map == null || map.size() == 0) {
            return true;
        }
        return false;
    }

}
