/**
 * UnSubscribe.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.weixin.in.event;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * 取消关注事件
 *
 * @author huangzhong_hui@163.com
 * @version 2014年12月28日
 */
@XStreamAlias("unsubscribe")
public class UnSubscribe extends InBase {
    private static final long serialVersionUID = -3982789086966593162L;

    public UnSubscribe() {
        super.setMsgType("event");
        super.setEvent(this.getClass().getAnnotation(XStreamAlias.class).value());
    }
}
