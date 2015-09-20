/**
 * EvtLocation.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.weixin.in.event;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * �ϱ�����λ���¼�<br>
 * �û�ͬ���ϱ�����λ�ú�ÿ�ν��빫�ںŻỰʱ�������ڽ���ʱ�ϱ�����λ�ã����ڽ���Ự��ÿ5���ϱ�һ�ε���λ�ã����ںſ����ڹ���ƽ̨��վ���޸��������á��ϱ�����λ��ʱ��΢�ŻὫ�ϱ�����λ���¼����͵���������д��URL
 *
 * @author huangzhong_hui@163.com
 * @version 2014��12��28��
 */
@XStreamAlias("LOCATION")
public class EvtLocation extends InBase {
    private static final long serialVersionUID = 2078614497108409428L;

    /**
     * ����λ��γ��
     */
    @XStreamAlias("Latitude")
    private double latitude;

    /**
     * ����λ�þ���
     */
    @XStreamAlias("Longitude")
    private double longitude;

    /**
     * ����λ�þ���
     */
    @XStreamAlias("Precision")
    private double precision;

    public EvtLocation() {
        super.setMsgType("event");
        super.setEvent(this.getClass().getAnnotation(XStreamAlias.class).value());
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @return the precision
     */
    public double getPrecision() {
        return precision;
    }

    /**
     * @param latitude
     *            the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @param longitude
     *            the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @param precision
     *            the precision to set
     */
    public void setPrecision(double precision) {
        this.precision = precision;
    }
}
