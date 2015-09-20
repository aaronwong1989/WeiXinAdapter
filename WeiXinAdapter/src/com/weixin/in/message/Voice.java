package com.weixin.in.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * 语音消息
 */
@XStreamAlias("voice")
public class Voice extends InBase {
    private static final long serialVersionUID = -8184170714610960965L;

    /**
     * 语音格式，如amr，speex等
     */
    @XStreamAlias("Format")
    private String format;

    /**
     * 语音消息媒体id，可以调用多媒体文件下载接口拉取数据
     */
    @XStreamAlias("MediaId")
    private String mediaId;

    /**
     * 语音识别结果，UTF8编码
     */
    @XStreamAlias("Recognition")
    private String recognition;

    /**
     * 语音消息
     */
    public Voice() {
        super.setMsgType(this.getClass().getAnnotation(XStreamAlias.class).value());
    }

    public String getFormat() {
        return this.format;
    }

    public String getMediaId() {
        return this.mediaId;
    }

    /**
     * @return the recognition
     */
    public String getRecognition() {
        return recognition;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    /**
     * @param recognition
     *            the recognition to set
     */
    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }

}
