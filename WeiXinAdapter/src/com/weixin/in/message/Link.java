package com.weixin.in.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * 链接消息
 */
@XStreamAlias("link")
public class Link extends InBase {

    private static final long serialVersionUID = -3004572384763168019L;

    /**
     * 消息描述
     */
    @XStreamAlias("Description")
    private String            description;

    /**
     * 消息标题
     */
    @XStreamAlias("Title")
    private String            title;

    /**
     * 消息链接
     */
    @XStreamAlias("Url")
    private String            url;

    /**
     * 链接消息
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
