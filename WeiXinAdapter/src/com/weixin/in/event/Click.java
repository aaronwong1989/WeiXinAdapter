/**
 * Click.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.weixin.in.event;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * �Զ���˵��¼� <br>
 * �û�����Զ���˵���΢�Ż�ѵ���¼����͸������ߣ���ע�⣬����˵������Ӳ˵�����������ϱ�
 *
 * @author huangzhong_hui@163.com
 * @version 2014��12��28��
 */
@XStreamAlias("CLICK")
public class Click extends InBase {

    private static final long serialVersionUID = 23837023418270917L;
    /**
     * �¼�KEYֵ�����Զ���˵��ӿ���KEYֵ��Ӧ
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
