/**
 * Scan.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.weixin.in.event;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * �ѹ�ע�û���ɨ���������ά���¼�
 *
 * @author huangzhong_hui@163.com
 * @version 2014��12��28��
 */
@XStreamAlias("SCAN")
public class Scan extends InBase {
    private static final long serialVersionUID = 5839599125794934732L;
    /**
     * �¼�KEYֵ����һ��32λ�޷�����������������ά��ʱ�Ķ�ά��scene_id
     */
    @XStreamAlias("EventKey")
    private long              eventKey;
    /**
     * ��ά���ticket����������ȡ��ά��ͼƬ
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
