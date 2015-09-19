package com.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beans.HttpUtil;
import com.beans.Settings;

/**
 * ��ControllerӦ��Ϊ����Ӧ�ò���,���ɼ�Ⱥ��,���Ե�����HA,���ṩtoken����.<br>
 * ��Ⱥ�и�server�����Լ���token expires_in 10minʱ,����������Ȼ���첽����������ȡ��token<br>
 * ��Ϊ��Ѷʵ�����¾�token����Ķ�ʱ��������token������<br>
 */
@Controller
@RequestMapping("/token")
public class AccessTokenController {
    /** ��ȡ��token���ٽ�ֵ,��ǰtoken����Чʱ��С�ڸ�ֵʱ��ȡ��token */
    private static final long   _10min        = 10 * 60 * 1000;
    /** token�����Чʱ�� */
    private static final long   _2hours       = 2 * 60 * 60 * 1000;
    /** ��ǰ��token */
    private static String       current_token = "";
    /** �ϴ�token����ʱ�� */
    private static long         last_f5_time  = 0;
    private static final Logger logger        = LogManager.getLogger(AccessTokenController.class);
    private static final Object TOKEN_LOCK    = new Object();

    /**
     * ��ȡtoken.
     *
     * @throws IOException
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public void getToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject token = new JSONObject();
        boolean avaliable = _2hours - (System.currentTimeMillis() - last_f5_time) > _10min;
        long _min = (_2hours - (System.currentTimeMillis() - last_f5_time)) / 1000 / 60;
        if (!"".equals(current_token)) {
            // ֱ�ӷ���current_token
            if (avaliable) {
                logger.info("token��Чʱ������{}min,ֱ�ӷ���current_token", _min);
                token = this.getCurrentToken();
            }
            // ����Ѷ��ȡ��token
            else {
                logger.info("token����ʧЧ({}min��),����Ѷ��ȡtoken", _min);
                token = this.getNewToken();
            }
        } else {
            logger.info("�״δ���Ѷ��ȡtoken");
            token = this.getNewToken();
        }
        response.getWriter().write(token.toJSONString());
    }

    /**
     * ����token json��
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��20��
     */
    private JSONObject getCurrentToken() {
        JSONObject token = new JSONObject();
        token.put("access_token", current_token);
        token.put("expires_in", (_2hours - (System.currentTimeMillis() - last_f5_time)) / 1000);
        return token;
    }

    /**
     * ����Ѷ��ȡtoken
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��20��
     * @return
     */
    private JSONObject getNewToken() {
        JSONObject token = new JSONObject();
        try {
            URI uri = Settings.getTokenUri();
            synchronized (TOKEN_LOCK) {
                // ���߳�ͬʱ��������Ѷ��ȡ��tokenʱ,���һ���߳̽�����δ��벢�ɹ���ȡ��token
                // ����������δ�����߳������ٴδ���Ѷ��ȡ
                boolean avaliable_just_now = _2hours - (System.currentTimeMillis() - last_f5_time) > _10min;
                if (avaliable_just_now) {
                    token = this.getCurrentToken();
                } else {
                    String resp = HttpUtil.sendGet(uri, null, null);
                    token = JSON.parseObject(resp);
                    if (token.containsKey("access_token")) {
                        last_f5_time = System.currentTimeMillis();
                        current_token = token.getString("access_token");
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
        return token;
    }
}
