/**
 * EvtLocation.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.weixin.in.event;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.weixin.in.InBase;

/**
 * 上报地理位置事件<br>
 * 用户同意上报地理位置后，每次进入公众号会话时，都会在进入时上报地理位置，或在进入会话后每5秒上报一次地理位置，公众号可以在公众平台网站中修改以上设置。上报地理位置时，微信会将上报地理位置事件推送到开发者填写的URL
 *
 * @author huangzhong_hui@163.com
 * @version 2014年12月28日
 */
@XStreamAlias("LOCATION")
public class EvtLocation extends InBase {
    private static final long serialVersionUID = 2078614497108409428L;

    /**
     * 地理位置纬度
     */
    @XStreamAlias("Latitude")
    private double latitude;

    /**
     * 地理位置经度
     */
    @XStreamAlias("Longitude")
    private double longitude;

    /**
     * 地理位置精度
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
