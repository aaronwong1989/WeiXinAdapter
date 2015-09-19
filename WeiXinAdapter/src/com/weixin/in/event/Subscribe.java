/**
 * Subscribe.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.weixin.in.event;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * ��ע�¼�<br>
 * ���� δ��ע�û���ɨ���������ά���¼�
 *
 * @author huangzhong_hui@163.com
 * @version 2014��12��28��
 */
@XStreamAlias("subscribe")
public class Subscribe extends InBase {
    private static final long serialVersionUID = 4675327962244785929L;
    /**
     * �¼�KEYֵ��qrscene_Ϊǰ׺������Ϊ��ά��Ĳ���ֵ
     */
    @XStreamAlias("EventKey")
    private String            eventKey;
    /**
     * ��ά���ticket����������ȡ��ά��ͼƬ
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
