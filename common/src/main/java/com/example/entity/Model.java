package com.example.entity;

import org.msgpack.annotation.Message;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shiyakun
 * @Description TODO
 */
@Message
public class Model implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 消息类型
     */
    private int type;

    /**
     * 消息内容
     */
    private String body;

    /**
     * 消息发送方
     */
    private int toId;

    /**
     * 消息接收方
     */
    private int fromId;

    private Date date = new Date();

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Model{" +
                "type=" + type +
                ", body='" + body + '\'' +
                ", toId=" + toId +
                ", fromId=" + fromId +
                ", date=" + date +
                '}';
    }
}
