package com.im.entity;


import java.util.Date;

/**
 * @author shiyakun
 * @Description TODO
 */
public class Message {

    private String body;

    /**
     * 消息类型
     */
    private int type;


    /**
     * 消息发送方
     */

    private long toId;

    /**
     * 消息接收方
     */

    private long fromId;

    private Date date;

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

    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
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
        return "Message{" +
                "type=" + type +
                ", body='" + body + '\'' +
                ", toId=" + toId +
                ", fromId=" + fromId +
                ", date=" + date +
                '}';
    }
}
