/**
 * Subscribe.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.weixin.in.event;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * 关注事件<br>
 * 或者 未关注用户的扫描带参数二维码事件
 *
 * @author huangzhong_hui@163.com
 * @version 2014年12月28日
 */
@XStreamAlias("subscribe")
public class Subscribe extends InBase {
    private static final long serialVersionUID = 4675327962244785929L;
    /**
     * 事件KEY值，qrscene_为前缀，后面为二维码的参数值
     */
    @XStreamAlias("EventKey")
    private String            eventKey;
    /**
     * 二维码的ticket，可用来换取二维码图片
     */
    @XStreamAlias("Ticket")
    private String            ticket;

    public Subscribe() {
        super.setMsgType("event");
        super.setEvent(this.getClass().getAnnotation(XStreamAlias.class).value());
    }

    /**
     * @return the eventKey
     */
    public String getEventKey() {
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
    public void setEventKey(String eventKey) {
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
