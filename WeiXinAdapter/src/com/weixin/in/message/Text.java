package com.weixin.in.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * �ı���Ϣ
 */
@XStreamAlias("text")
public class Text extends InBase {
    private static final long serialVersionUID = 8079108075834112883L;

    /**
     * �ı���Ϣ����
     */
    @XStreamAlias("Content")
    private String            content;

    /**
     * �ı���Ϣ
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
