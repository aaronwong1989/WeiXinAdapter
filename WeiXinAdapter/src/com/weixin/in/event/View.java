/**
 * Click.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.weixin.in.event;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * 点击菜单跳转链接时的事件推送
 *
 * @author huangzhong_hui@163.com
 * @version 2014年12月28日
 */
@XStreamAlias("VIEW")
public class View extends InBase {

    private static final long serialVersionUID = 23837023418270917L;
    /**
     * 事件KEY值，设置的跳转URL
     */
    @XStreamAlias("EventKey")
    private String            eventKey;

    public View() {
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
     * @param eventKey
     *            the eventKey to set
     */
    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
