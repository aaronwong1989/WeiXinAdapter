package com.weixin.in.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * 视频消息
 */
@XStreamAlias("video")
public class Video extends InBase {
    private static final long serialVersionUID = 2324804676439190255L;

    /**
     * 视频消息媒体id，可以调用多媒体文件下载接口拉取数据
     */
    @XStreamAlias("MediaId")
    private String mediaId;

    /**
     * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据
     */
    @XStreamAlias("ThumbMediaId")
    private String thumbMediaId;

    /**
     * 视频消息
     */
    public Video() {
        super.setMsgType(this.getClass().getAnnotation(XStreamAlias.class).value());
    }

    public String getMediaId() {
        return this.mediaId;
    }

    public String getThumbMediaId() {
        return this.thumbMediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

}
