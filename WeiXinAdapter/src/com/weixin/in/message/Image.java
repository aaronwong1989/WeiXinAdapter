package com.weixin.in.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * ͼƬ��Ϣ
 */
@XStreamAlias("image")
public class Image extends InBase {

    private static final long serialVersionUID = -1670358377253557784L;

    /**
     * ͼƬ��Ϣý��id�����Ե��ö�ý���ļ����ؽӿ���ȡ����
     */
    @XStreamAlias("MediaId")
    private String            mediaId;

    /**
     * ͼƬ����
     */
    @XStreamAlias("PicUrl")
    private String            picUrl;

    /**
     * ͼƬ��Ϣ
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
