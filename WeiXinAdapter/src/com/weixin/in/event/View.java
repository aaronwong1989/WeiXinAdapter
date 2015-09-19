/**
 * Click.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.weixin.in.event;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * ����˵���ת����ʱ���¼�����
 *
 * @author huangzhong_hui@163.com
 * @version 2014��12��28��
 */
@XStreamAlias("VIEW")
public class View extends InBase {

    private static final long serialVersionUID = 23837023418270917L;
    /**
     * �¼�KEYֵ�����õ���תURL
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
