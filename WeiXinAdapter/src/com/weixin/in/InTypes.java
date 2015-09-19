/**
 * InTypes.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.weixin.in;

/**
 * 上行消息及事件类型
 *
 * @author huangzhong_hui@163.com
 * @version 2014年12月28日
 */
public enum InTypes {
    // 事件
    Click(11), EvtLocation(16), Image(2), Link(3), MsgLocation(6), Scan(13), Subscribe(14), // 消息
    Text(1), UnSubscribe(15), Video(4), View(12), Voice(5);

    private int value;

    InTypes(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
