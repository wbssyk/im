package com.im;


import com.google.protobuf.MapEntry;
import com.google.protobuf.Timestamp;
import com.im.code.TypeData;
import com.im.entity.Ack;
import com.im.entity.Message;
import com.im.entity.Model;
import com.im.listener.MsgListener;

/**
 * @author shiyakun
 * @Description TODO
 */
public class TestListener implements MsgListener {



    @Override
    public void receiveMsg(Message model) {
        //1：在这里做进一步数据处理
        System.out.println(model);
    }

    public static void main(String[] args) {
        Timestamp.Builder builder1 = Timestamp.newBuilder();
        Timestamp build = builder1.setSeconds(System.currentTimeMillis()).build();
        long seconds = build.getSeconds();
        System.out.println(seconds);

    }
}
