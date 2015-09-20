/**
 * Settings.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.beans;

import java.net.URISyntaxException;
import java.util.Properties;

/**
 * ������Ϣ
 *
 * @author huangzhong_hui@163.com
 * @version 2014��12��20��
 */
public class Settings {

    /**
     * ��ָ������Bean�л�ȡָ��key��Boolean��ֵ
     *
     * @param settingBean
     *            ����Bean��
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
     * ��ָ������Bean�л�ȡָ��key��Double��ֵ
     *
     * @param settingBean
     *            ����Bean��
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
     * ��ָ������Bean�л�ȡָ��key��Float��ֵ
     *
     * @param settingBean
     *            ����Bean��
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
     * ��ָ������Bean�л�ȡָ��key������ֵ
     *
     * @param settingBean
     *            ����Bean����
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
     * ��ָ������Bean�л�ȡָ��key��Long��ֵ
     *
     * @param settingBean
     *            ����Bean��
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
     * ��ָ������Bean�л�ȡָ��key��String��ֵ
     *
     * @param settingBean
     *            ����Bean��
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
     * ��ȡTokenUri
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��20��
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
     * ���ݴ������ȡ��Ѷ������Ϣ
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��20��
     * @param errcode
     * @return
     */
    public static String getTXErrorMessage(String errcode) {
        return getStringValue("tx_error_code", errcode);
    }
}
