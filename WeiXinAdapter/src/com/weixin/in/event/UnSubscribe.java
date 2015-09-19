/**
 * UnSubscribe.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.weixin.in.event;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * ȡ����ע�¼�
 *
 * @author huangzhong_hui@163.com
 * @version 2014��12��28��
 */
@XStreamAlias("unsubscribe")
public class UnSubscribe extends InBase {
    private static final long serialVersionUID = -3982789086966593162L;

    public UnSubscribe() {
        super.setMsgType("event");
        super.setEvent(this.getClass().getAnnotation(XStreamAlias.class).value());
    }
}
