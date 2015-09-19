package com.weixin.in.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * ����λ����Ϣ
 */
@XStreamAlias("location")
public class MsgLocation extends InBase {

    private static final long serialVersionUID = -5746218577102995047L;

    /**
     * ����λ����Ϣ
     */
    @XStreamAlias("Label")
    private String            label;

    /**
     * ����λ��ά��
     */
    @XStreamAlias("Location_X")
    private double            location_X;

    /**
     * ����λ�þ���
     */
    @XStreamAlias("Location_Y")
    private double            location_Y;

    /**
     * ��ͼ���Ŵ�С
     */
    @XStreamAlias("Scale")
    private int               scale;

    /**
     * ����λ����Ϣ
     */
    public MsgLocation() {
        super.setMsgType(this.getClass().getAnnotation(XStreamAlias.class).value());
    }

    public String getLabel() {
        return this.label;
    }

    public double getLocation_X() {
        return this.location_X;
    }

    public double getLocation_Y() {
        return this.location_Y;
    }

    public int getScale() {
        return this.scale;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLocation_X(double location_X) {
        this.location_X = location_X;
    }

    public void setLocation_Y(double location_Y) {
        this.location_Y = location_Y;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

}
