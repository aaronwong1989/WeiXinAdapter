package com.beans;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 获取token<br>
 * 若发现自己的token expires_in 10min时,向local_token服务发起该请求获取新token<br>
 */
public class AccessTokenUtil {
    /** token最大有效时长,伪2hour,避免token过期 */
    private static final long   _2hours         = 2 * 60 * 59 * 1000;
    /** 当前的token */
    private static String       current_token   = "";
    /** 上次token更新时间 */
    private static long         last_f5_time    = 0;
    private static final String LOCAL_TOKEN_URL = Settings.getStringValue("settings", "local_token_url");
    private static final Logger logger          = LogManager.getLogger(AccessTokenUtil.class);
    private static final Object TOKEN_LOCK      = new Object();

    /**
     * 获取token.
     *
     * @throws IOException
     */
    public static String getToken() throws IOException {
        boolean avaliable = _2hours - (System.currentTimeMillis() - last_f5_time) > 0;
        long _min = (_2hours - (System.currentTimeMillis() - last_f5_time)) / 1000 / 60;

        if ("".equals(current_token)) {
            logger.info("首次从{}获取token", LOCAL_TOKEN_URL);
            return getNewToken();
        } else {
            // 直接返回current_token
            if (avaliable) {
                logger.debug("token有效时间尚有{}min,直接返回current_token", _min);
                return current_token;
            }
            // 从local_token_server获取新token
            else {
                logger.info("token即将失效({}min内),从{}获取token", _min, LOCAL_TOKEN_URL);
                return getNewToken();
            }
        }
    }

    /**
     * 从腾讯获取token<br>
     * 交易中如果发现token已过期应立即执行该方法刷新
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月20日
     * @return
     */
    public static String getNewToken() {
        JSONObject token = new JSONObject();
        try {
            URI uri = new URI(LOCAL_TOKEN_URL);
            synchronized (TOKEN_LOCK) {
                // 多线程同时竞争从local_token_server获取新token时,如果一个线程进入这段代码并成功获取了token
                // 后续进入这段代码的线程无需再次从local_token_server获取
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
                        logger.error("获取token失败,{},{}", resp, Settings.getTXErrorMessage(token.getString("errcode")));
                    }
                }
            }
        } catch (URISyntaxException e) {
            logger.error("请检查classpath:settings.properties", e);
        } catch (IOException e) {
            logger.error("获取token时通讯异常", e);
        }
        return "";
    }
}
