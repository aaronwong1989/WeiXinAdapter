package com.weixin.in.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * 文本消息
 */
@XStreamAlias("text")
public class Text extends InBase {
    private static final long serialVersionUID = 8079108075834112883L;

    /**
     * 文本消息内容
     */
    @XStreamAlias("Content")
    private String            content;

    /**
     * 文本消息
     */
    public Text() {
        super.setMsgType(this.getClass().getAnnotation(XStreamAlias.class).value());
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
