package com.weixin.in.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * ������Ϣ
 */
@XStreamAlias("link")
public class Link extends InBase {

    private static final long serialVersionUID = -3004572384763168019L;

    /**
     * ��Ϣ����
     */
    @XStreamAlias("Description")
    private String            description;

    /**
     * ��Ϣ����
     */
    @XStreamAlias("Title")
    private String            title;

    /**
     * ��Ϣ����
     */
    @XStreamAlias("Url")
    private String            url;

    /**
     * ������Ϣ
     */
    public Link() {
        super.setMsgType(this.getClass().getAnnotation(XStreamAlias.class).value());
    }

    public String getDescription() {
        return this.description;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
