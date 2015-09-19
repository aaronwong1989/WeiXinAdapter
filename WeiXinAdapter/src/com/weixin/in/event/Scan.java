/**
 * Scan.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.weixin.in.event;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * 已关注用户的扫描带参数二维码事件
 *
 * @author huangzhong_hui@163.com
 * @version 2014年12月28日
 */
@XStreamAlias("SCAN")
public class Scan extends InBase {
    private static final long serialVersionUID = 5839599125794934732L;
    /**
     * 事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
     */
    @XStreamAlias("EventKey")
    private long              eventKey;
    /**
     * 二维码的ticket，可用来换取二维码图片
     */
    @XStreamAlias("Ticket")
    private String            ticket;

    public Scan() {
        super.setMsgType("event");
        super.setEvent(this.getClass().getAnnotation(XStreamAlias.class).value());
    }

    /**
     * @return the eventKey
     */
    public long getEventKey() {
        return eventKey;
    }

    /**
     * @return the ticket
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * @param eventKey
     *            the eventKey to set
     */
    public void setEventKey(long eventKey) {
        this.eventKey = eventKey;
    }

    /**
     * @param ticket
     *            the ticket to set
     */
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
