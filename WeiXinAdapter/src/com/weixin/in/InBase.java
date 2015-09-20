package com.weixin.in;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 上行消息&事件
 */
@XStreamAlias("xml")
public class InBase extends ToStringBase {
    private static final long serialVersionUID = -6112981260440719542L;

    /**
     * 消息创建时间 （整型）
     */
    @XStreamAlias("CreateTime")
    private long createTime;

    /**
     * 事件类型<br>
     * MsgType="event"时使用
     */
    @XStreamAlias("Event")
    private String event;

    /**
     * 发送方帐号（一个OpenID）
     */
    @XStreamAlias("FromUserName")
    private String fromUserName;

    /**
     * 消息id,64位整型
     */
    @XStreamAlias("MsgId")
    private long msgId;

    /**
     * 消息类型
     */
    @XStreamAlias("MsgType")
    private String msgType;

    /**
     * 开发者微信号
     */
    @XStreamAlias("ToUserName")
    private String toUserName;

    public long getCreateTime() {
        return this.createTime;
    }

    /**
     * @return the event
     */
    public String getEvent() {
        return event;
    }

    public String getFromUserName() {
        return this.fromUserName;
    }

    public long getMsgId() {
        return this.msgId;
    }

    public String getMsgType() {
        return this.msgType;
    }

    public String getToUserName() {
        return this.toUserName;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    /**
     * @param event
     *            the event to set
     */
    public void setEvent(String event) {
        this.event = event;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

}
