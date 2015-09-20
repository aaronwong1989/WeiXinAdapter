package com.weixin.in;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * ������Ϣ&�¼�
 */
@XStreamAlias("xml")
public class InBase extends ToStringBase {
    private static final long serialVersionUID = -6112981260440719542L;

    /**
     * ��Ϣ����ʱ�� �����ͣ�
     */
    @XStreamAlias("CreateTime")
    private long createTime;

    /**
     * �¼�����<br>
     * MsgType="event"ʱʹ��
     */
    @XStreamAlias("Event")
    private String event;

    /**
     * ���ͷ��ʺţ�һ��OpenID��
     */
    @XStreamAlias("FromUserName")
    private String fromUserName;

    /**
     * ��Ϣid,64λ����
     */
    @XStreamAlias("MsgId")
    private long msgId;

    /**
     * ��Ϣ����
     */
    @XStreamAlias("MsgType")
    private String msgType;

    /**
     * ������΢�ź�
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
