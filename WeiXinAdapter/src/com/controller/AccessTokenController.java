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
 * 该Controller应作为独立应用部署,不可集群化,可以单机或HA,仅提供token服务.<br>
 * 集群中各server发现自己的token expires_in 10min时,正常发起交易然后异步发起该请求获取新token<br>
 * 因为腾讯实现了新旧token交替的短时间内两个token都可用<br>
 */
@Controller
@RequestMapping("/token")
public class AccessTokenController {
    /** 获取新token的临界值,当前token的有效时间小于该值时获取新token */
    private static final long   _10min        = 10 * 60 * 1000;
    /** token最大有效时长 */
    private static final long   _2hours       = 2 * 60 * 60 * 1000;
    /** 当前的token */
    private static String       current_token = "";
    /** 上次token更新时间 */
    private static long         last_f5_time  = 0;
    private static final Logger logger        = LogManager.getLogger(AccessTokenController.class);
    private static final Object TOKEN_LOCK    = new Object();

    /**
     * 获取token.
     *
     * @throws IOException
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public void getToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject token = new JSONObject();
        boolean avaliable = _2hours - (System.currentTimeMillis() - last_f5_time) > _10min;
        long _min = (_2hours - (System.currentTimeMillis() - last_f5_time)) / 1000 / 60;
        if (!"".equals(current_token)) {
            // 直接返回current_token
            if (avaliable) {
                logger.info("token有效时间尚有{}min,直接返回current_token", _min);
                token = this.getCurrentToken();
            }
            // 从腾讯获取新token
            else {
                logger.info("token即将失效({}min内),从腾讯获取token", _min);
                token = this.getNewToken();
            }
        } else {
            logger.info("首次从腾讯获取token");
            token = this.getNewToken();
        }
        response.getWriter().write(token.toJSONString());
    }

    /**
     * 生成token json串
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月20日
     */
    private JSONObject getCurrentToken() {
        JSONObject token = new JSONObject();
        token.put("access_token", current_token);
        token.put("expires_in", (_2hours - (System.currentTimeMillis() - last_f5_time)) / 1000);
        return token;
    }

    /**
     * 从腾讯获取token
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月20日
     * @return
     */
    private JSONObject getNewToken() {
        JSONObject token = new JSONObject();
        try {
            URI uri = Settings.getTokenUri();
            synchronized (TOKEN_LOCK) {
                // 多线程同时竞争从腾讯获取新token时,如果一个线程进入这段代码并成功获取了token
                // 后续进入这段代码的线程无需再次从腾讯获取
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
                        logger.error("获取token失败,{},{}", resp, Settings.getTXErrorMessage(token.getString("errcode")));
                    }
                }
            }
        } catch (URISyntaxException e) {
            logger.error("请检查classpath:settings.properties", e);
        } catch (IOException e) {
            logger.error("获取token时通讯异常", e);
        }
        return token;
    }
}
