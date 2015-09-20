/**
 * Settings.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.beans;

import java.net.URISyntaxException;
import java.util.Properties;

/**
 * 配置信息
 *
 * @author huangzhong_hui@163.com
 * @version 2014年12月20日
 */
public class Settings {

    /**
     * 从指定配置Bean中获取指定key的Boolean型值
     *
     * @param settingBean
     *            配置Bean名
     * @param key
     *            key
     * @return Boolean value
     */
    public static boolean getBooleanValue(String settingBean, String key) {

        Properties prop = (Properties) SpringContextUtils.getBean(settingBean);
        String sValue = prop.getProperty(key);
        if (sValue == null || "".equals(sValue)) {
            sValue = "false";
        }
        try {
            return Boolean.parseBoolean(sValue);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 从指定配置Bean中获取指定key的Double型值
     *
     * @param settingBean
     *            配置Bean名
     * @param key
     *            key
     * @return Double value
     */
    public static double getDoubleValue(String settingBean, String key) {

        Properties prop = (Properties) SpringContextUtils.getBean(settingBean);
        String sValue = prop.getProperty(key);
        if (sValue == null || "".equals(sValue)) {
            sValue = "0.0";
        }
        try {
            return Double.parseDouble(sValue);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * 从指定配置Bean中获取指定key的Float型值
     *
     * @param settingBean
     *            配置Bean名
     * @param key
     *            key
     * @return Float value
     */
    public static float getFloatValue(String settingBean, String key) {

        Properties prop = (Properties) SpringContextUtils.getBean(settingBean);
        String sValue = prop.getProperty(key);
        if (sValue == null || "".equals(sValue)) {
            sValue = "0.0";
        }
        try {
            return Float.parseFloat(sValue);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    /**
     * 从指定配置Bean中获取指定key的整数值
     *
     * @param settingBean
     *            配置Bean名称
     * @param key
     *            key
     * @return int value
     */
    public static int getIntValue(String settingBean, String key) {
        Properties prop = (Properties) SpringContextUtils.getBean(settingBean);
        String sValue = prop.getProperty(key);
        if (sValue == null || "".equals(sValue)) {
            sValue = "0";
        }
        try {
            return Integer.parseInt(sValue);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 从指定配置Bean中获取指定key的Long型值
     *
     * @param settingBean
     *            配置Bean名
     * @param key
     *            key
     * @return Long value
     */
    public static Long getLongValue(String settingBean, String key) {

        Properties prop = (Properties) SpringContextUtils.getBean(settingBean);
        String sValue = prop.getProperty(key);
        if (sValue == null || "".equals(sValue)) {
            sValue = "0";
        }
        try {
            return Long.parseLong(sValue);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    /**
     * 从指定配置Bean中获取指定key的String型值
     *
     * @param settingBean
     *            配置Bean名
     * @param key
     *            key
     * @return String value
     */
    public static String getStringValue(String settingBean, String key) {
        Properties prop = (Properties) SpringContextUtils.getBean(settingBean);
        String sValue = prop.getProperty(key);
        if (sValue == null) { return ""; }
        return sValue;
    }

    /**
     * 获取TokenUri
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月20日
     * @return
     * @throws URISyntaxException
     */
    public static String getTokenUrl() throws URISyntaxException {
        String url = Settings.getStringValue("settings", "token_url");
        String appid = Settings.getStringValue("settings", "appid");
        String secret = Settings.getStringValue("settings", "secret");
        url = url.replace("$[APPID]", appid);
        url = url.replace("$[APPSECRET]", secret);
        return url;
    }

    /**
     * 根据错误码获取腾讯错误信息
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月20日
     * @param errcode
     * @return
     */
    public static String getTXErrorMessage(String errcode) {
        return getStringValue("tx_error_code", errcode);
    }
}
