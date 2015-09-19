package com.weixin.in.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * 图片消息
 */
@XStreamAlias("image")
public class Image extends InBase {

    private static final long serialVersionUID = -1670358377253557784L;

    /**
     * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据
     */
    @XStreamAlias("MediaId")
    private String            mediaId;

    /**
     * 图片链接
     */
    @XStreamAlias("PicUrl")
    private String            picUrl;

    /**
     * 图片消息
     */
    public Image() {
        super.setMsgType(this.getClass().getAnnotation(XStreamAlias.class).value());
    }

    public String getMediaId() {
        return this.mediaId;
    }

    public String getPicUrl() {
        return this.picUrl;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

}
