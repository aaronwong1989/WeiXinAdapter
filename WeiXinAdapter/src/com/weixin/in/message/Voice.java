package com.weixin.in.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * ������Ϣ
 */
@XStreamAlias("voice")
public class Voice extends InBase {
    private static final long serialVersionUID = -8184170714610960965L;

    /**
     * ������ʽ����amr��speex��
     */
    @XStreamAlias("Format")
    private String format;

    /**
     * ������Ϣý��id�����Ե��ö�ý���ļ����ؽӿ���ȡ����
     */
    @XStreamAlias("MediaId")
    private String mediaId;

    /**
     * ����ʶ������UTF8����
     */
    @XStreamAlias("Recognition")
    private String recognition;

    /**
     * ������Ϣ
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
