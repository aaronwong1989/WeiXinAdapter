/**
 * Click.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.weixin.in.event;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * 自定义菜单事件 <br>
 * 用户点击自定义菜单后，微信会把点击事件推送给开发者，请注意，点击菜单弹出子菜单，不会产生上报
 *
 * @author huangzhong_hui@163.com
 * @version 2014年12月28日
 */
@XStreamAlias("CLICK")
public class Click extends InBase {

    private static final long serialVersionUID = 23837023418270917L;
    /**
     * 事件KEY值，与自定义菜单接口中KEY值对应
     */
    @XStreamAlias("EventKey")
    private String            eventKey;

    public Click() {
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
