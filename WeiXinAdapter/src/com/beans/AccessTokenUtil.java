package com.beans;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * ��ȡtoken<br>
 * �������Լ���token expires_in 10minʱ,��local_token������������ȡ��token<br>
 */
public class AccessTokenUtil {
    /** token�����Чʱ��,α2hour,����token���� */
    private static final long   _2hours         = 2 * 60 * 59 * 1000;
    /** ��ǰ��token */
    private static String       current_token   = "";
    /** �ϴ�token����ʱ�� */
    private static long         last_f5_time    = 0;
    private static final String LOCAL_TOKEN_URL = Settings.getStringValue("settings", "local_token_url");
    private static final Logger logger          = LogManager.getLogger(AccessTokenUtil.class);
    private static final Object TOKEN_LOCK      = new Object();

    /**
     * ��ȡtoken.
     *
     * @throws IOException
     */
    public static String getToken() throws IOException {
        boolean avaliable = _2hours - (System.currentTimeMillis() - last_f5_time) > 0;
        long _min = (_2hours - (System.currentTimeMillis() - last_f5_time)) / 1000 / 60;

        if ("".equals(current_token)) {
            logger.info("�״δ�{}��ȡtoken", LOCAL_TOKEN_URL);
            return getNewToken();
        } else {
            // ֱ�ӷ���current_token
            if (avaliable) {
                logger.debug("token��Чʱ������{}min,ֱ�ӷ���current_token", _min);
                return current_token;
            }
            // ��local_token_server��ȡ��token
            else {
                logger.info("token����ʧЧ({}min��),��{}��ȡtoken", _min, LOCAL_TOKEN_URL);
                return getNewToken();
            }
        }
    }

    /**
     * ����Ѷ��ȡtoken<br>
     * �������������token�ѹ���Ӧ����ִ�и÷���ˢ��
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��20��
     * @return
     */
    public static String getNewToken() {
        JSONObject token = new JSONObject();
        try {
            URI uri = new URI(LOCAL_TOKEN_URL);
            synchronized (TOKEN_LOCK) {
                // ���߳�ͬʱ������local_token_server��ȡ��tokenʱ,���һ���߳̽�����δ��벢�ɹ���ȡ��token
                // ����������δ�����߳������ٴδ�local_token_server��ȡ
                boolean avaliable_just_now = _2hours - (System.currentTimeMillis() - last_f5_time) > 0;
                if (avaliable_just_now) {
                    return current_token;
                } else {
                    String resp = HttpUtil.sendGet(uri, null, null);
                    token = JSON.parseObject(resp);
                    if (token.containsKey("access_token")) {
                        last_f5_time = System.currentTimeMillis() - 10 * token.getIntValue("expires_in");
                        current_token = token.getString("access_token");
                        return current_token;
                    } else {
                        logger.error("��ȡtokenʧ��,{},{}", resp, Settings.getTXErrorMessage(token.getString("errcode")));
                    }
                }
            }
        } catch (URISyntaxException e) {
            logger.error("����classpath:settings.properties", e);
        } catch (IOException e) {
            logger.error("��ȡtokenʱͨѶ�쳣", e);
        }
        return "";
    }
}
