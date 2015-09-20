package com.weixin.in.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * ��Ƶ��Ϣ
 */
@XStreamAlias("video")
public class Video extends InBase {
    private static final long serialVersionUID = 2324804676439190255L;

    /**
     * ��Ƶ��Ϣý��id�����Ե��ö�ý���ļ����ؽӿ���ȡ����
     */
    @XStreamAlias("MediaId")
    private String mediaId;

    /**
     * ��Ƶ��Ϣ����ͼ��ý��id�����Ե��ö�ý���ļ����ؽӿ���ȡ����
     */
    @XStreamAlias("ThumbMediaId")
    private String thumbMediaId;

    /**
     * ��Ƶ��Ϣ
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
