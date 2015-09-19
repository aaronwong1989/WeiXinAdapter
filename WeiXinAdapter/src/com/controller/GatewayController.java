package com.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.beans.AccessTokenUtil;
import com.beans.LoggerEnum;
import com.weixin.in.InBase;
import com.weixin.in.InFactory;
import com.weixin.in.InHandler;
import com.weixin.in.event.Click;
import com.weixin.in.event.EvtLocation;
import com.weixin.in.event.Scan;
import com.weixin.in.event.Subscribe;
import com.weixin.in.event.UnSubscribe;
import com.weixin.in.event.View;
import com.weixin.in.message.Image;
import com.weixin.in.message.Link;
import com.weixin.in.message.MsgLocation;
import com.weixin.in.message.Text;
import com.weixin.in.message.Video;
import com.weixin.in.message.Voice;

/**
 * 腾讯交互网关
 */
@Controller
@RequestMapping("/gateway")
public class GatewayController {
    private static final String ENCODING  = "UTF-8";
    private static final Logger ERRLOGGER = LogManager.getLogger(LoggerEnum.errorLogger.value());
    private static final Logger LOGGER    = LogManager.getLogger(GatewayController.class);
    private static final String NAME_G    = "【腾讯网关请求: IN】";
    private static final String NAME_V    = "【网关URL校验】";

    /**
     * 【腾讯网关请求: IN】.
     *
     * @throws IOException
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void gateway(HttpServletRequest request, HttpServletResponse response) {
        long start = System.currentTimeMillis();
        try {
            request.setCharacterEncoding(ENCODING);
            String channelId = StringUtils.defaultIfEmpty(request.getParameter("channel"), "");
            String reqData = IOUtils.toString(request.getInputStream(), ENCODING);
            LOGGER.info("{}channelId:{}", NAME_G, channelId);
            LOGGER.info("{}reqXml:\n{}", NAME_G, reqData);

            // 上行消息&事件
            InBase in = InFactory.getInstance().fromXml(reqData);
            LOGGER.info("{}reqObject:{}", NAME_G, in.getClass().getName());

            // 根据不同的消息类型进行不同处理
            handleInAndResponse(response, in);

        } catch (Exception e) {
            ERRLOGGER.error(e.getMessage(), e);
        }
        LOGGER.info("{}结束,耗时{}ms", NAME_G, System.currentTimeMillis() - start);
    }

    /**
     * 网关校验.
     *
     * @throws IOException
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public void gatewayVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            String token = AccessTokenUtil.getToken();
            String signature = StringUtils.trimToEmpty(request.getParameter("signature"));
            String timestamp = StringUtils.trimToEmpty(request.getParameter("timestamp"));
            String nonce = StringUtils.trimToEmpty(request.getParameter("nonce"));
            String echostr = StringUtils.trimToEmpty(request.getParameter("echostr"));

            LOGGER.info("{}signature:{}", NAME_V, signature);
            LOGGER.info("{}timestamp:{}", NAME_V, timestamp);
            LOGGER.info("{}nonce:{}", NAME_V, nonce);
            LOGGER.info("{}token:{}", NAME_V, token);

            String[] ArrTmp = { token, timestamp, nonce };
            Arrays.sort(ArrTmp);
            StringBuffer sb = new StringBuffer();
            for (String element : ArrTmp) {
                sb.append(element);
            }
            String mySignature = this.Encrypt(sb.toString());
            if (mySignature.equals(signature)) {
                // GET请求原样返回echostr参数内容，则接入生效
                response.getWriter().write(echostr);
                LOGGER.info("{}return echostr:{}", NAME_V, echostr);
                LOGGER.info("{}成功", NAME_V);
                return;
            }
        } catch (Exception e) {
            ERRLOGGER.error(e.getMessage(), e);
        }
        ERRLOGGER.error("{}失败", NAME_V);
    }

    private String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (byte bt : bts) {
            tmp = Integer.toHexString(bt & 0xFF);
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    private String Encrypt(String strSrc) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update(bt);
            // to HexString
            strDes = this.bytes2Hex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            ERRLOGGER.error("Invalid algorithm.");
        }
        return strDes;
    }

    /**
     * 消息&事件处理及响应
     *
     * @param response
     * @param in
     * @throws IOException
     */
    private void handleInAndResponse(HttpServletResponse response, InBase in) throws IOException {
        InHandler hander = InHandler.getInstance();
        if (in instanceof Click) {
            hander.doClick(response, (Click) in);
        } else if (in instanceof View) {
            hander.doView(response, (View) in);
        } else if (in instanceof Text) {
            hander.doText(response, (Text) in);
        } else if (in instanceof Voice) {
            hander.doVoice(response, (Voice) in);
        } else if (in instanceof Image) {
            hander.doImage(response, (Image) in);
        } else if (in instanceof Link) {
            hander.doLink(response, (Link) in);
        } else if (in instanceof Video) {
            hander.doVideo(response, (Video) in);
        } else if (in instanceof MsgLocation) {
            hander.doMsgLocation(response, (MsgLocation) in);
        } else if (in instanceof EvtLocation) {
            hander.doEvtLocation(response, (EvtLocation) in);
        } else if (in instanceof Scan) {
            hander.doScan(response, (Scan) in);
        } else if (in instanceof Subscribe) {
            hander.doSubscribe(response, (Subscribe) in);
        } else if (in instanceof UnSubscribe) {
            hander.doUnSubscribe(response, (UnSubscribe) in);
        }
    }
}
