/**
 * HttpUtil.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.beans;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * http工具类
 *
 * @author huangzhong_hui@163.com
 * @version 2014年12月20日
 */
public class HttpUtil {
    /** 默认字符集 */
    private static final String                                 DEFAULT_ENCODING = "UTF-8";
    /** errcode */
    private static final String                                 ERROR_CODE       = "errcode";
    /** logger */
    private static final Logger                                 logger           = LogManager.getLogger(HttpUtil.class);
    /** http请求工厂 */
    private static final HttpComponentsClientHttpRequestFactory requestFactory   = new HttpComponentsClientHttpRequestFactory();
    /** access_token */
    private static final String                                 TOKEN            = "access_token";
    /** TOKEN_TIMEOUNT */
    private static final int                                    TOKEN_TIMEOUNT   = 42001;

    /**
     * 获得{"errcode":"code"}样式的错误码
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月20日
     * @param errcode
     * @return
     */
    public static String getJsonerrcode(Object errcode) {
        return "{\"" + ERROR_CODE + "\":\"" + errcode + "\"}";
    }

    /**
     * 采用指定编码发送get请求并附带URL参数
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月20日
     * @param uri
     * @param encoding
     *            default is null (UTF-8)
     * @param urlParams
     *            default is null
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static String sendGet(URI uri, String encoding, Map<String, String> urlParams) throws IOException,
            URISyntaxException {
        long begin = System.currentTimeMillis();
        if (StringUtils.isEmpty(encoding)) {
            encoding = DEFAULT_ENCODING;
        }
        String retStr = "", resp = "";
        if (urlParams != null) {
            URIBuilder builder = new URIBuilder(uri);
            for (Entry<String, String> e : urlParams.entrySet()) {
                builder = builder.addParameter(URLEncoder.encode(e.getKey(), encoding),
                        URLEncoder.encode(e.getValue(), encoding));
            }
            uri = builder.build();
        }
        logger.info("send GET to : {}", uri.toString());

        ClientHttpRequest request = requestFactory.createRequest(uri, HttpMethod.GET);
        ClientHttpResponse response = request.execute();
        HttpStatus status = response.getStatusCode();
        if (HttpStatus.OK.value() == status.value()) {
            resp = IOUtils.toString(response.getBody(), encoding);
            retStr = resp;
        } else {
            retStr = getJsonerrcode(status.value());
        }
        logger.info("response status : {}", status.toString());
        logger.info("recive data : {}", resp);
        logger.info("GET请求耗时 : {} ms", System.currentTimeMillis() - begin);

        return retStr;
    }

    /**
     * 采用默认编码发送空的post请求
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月20日
     * @param uri
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static String sendPost(URI uri) throws IOException, URISyntaxException {
        return sendPost(uri, DEFAULT_ENCODING);
    }

    /**
     * 采用指定编码发送空的post请求
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月20日
     * @param uri
     * @param encoding
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static String sendPost(URI uri, String encoding) throws IOException, URISyntaxException {
        return sendPost(uri, DEFAULT_ENCODING, null);
    }

    /**
     * 采用指定编码发送非空数据的post请求
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月20日
     * @param uri
     * @param encoding
     * @param trxData
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static String sendPost(URI uri, String encoding, String trxData) throws IOException, URISyntaxException {
        return sendPost(uri, encoding, trxData, null);
    }

    /**
     * 采用指定编码发送非空数据的post请求并附带URL参数
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月20日
     * @param uri
     * @param encoding
     * @param trxData
     * @param urlParams
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static String sendPost(URI uri, String encoding, String trxData, Map<String, String> urlParams) throws IOException,
            URISyntaxException {
        long begin = System.currentTimeMillis();
        if (StringUtils.isEmpty(encoding)) {
            encoding = DEFAULT_ENCODING;
        }
        String retStr = "", resp = "";
        if (urlParams != null) {
            URIBuilder builder = new URIBuilder(uri);
            for (Entry<String, String> e : urlParams.entrySet()) {
                builder = builder.addParameter(URLEncoder.encode(e.getKey(), encoding),
                        URLEncoder.encode(e.getValue(), encoding));
            }
            uri = builder.build();
        }
        logger.info("send POST to : {}", uri.toString());
        logger.info("send trxData : {}", trxData);

        ClientHttpRequest request = requestFactory.createRequest(uri, HttpMethod.POST);
        // 有发送数据
        if (trxData != null) {
            OutputStream out = request.getBody();
            out.write(trxData.getBytes(encoding));
        }
        ClientHttpResponse response = request.execute();
        HttpStatus status = response.getStatusCode();
        if (HttpStatus.OK.value() == status.value()) {
            resp = IOUtils.toString(response.getBody(), encoding);
            retStr = resp;
        } else {
            retStr = getJsonerrcode(status.value());
        }
        logger.info("response status : {}", status.toString());
        logger.info("recive data : {}", resp);
        logger.info("POST请求耗时 : {} ms", System.currentTimeMillis() - begin);

        return retStr;
    }

    /**
     * 向腾讯发送带token的请求<br>
     * token超时,获取新的TOKEN重新发起<br>
     * 采用指定编码发送非空数据的post请求并附带URL参数
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月20日
     * @param uri
     * @param encoding
     * @param trxData
     * @param urlParams
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static JSONObject sendPostWithToken(URI uri, String encoding, String trxData, Map<String, String> urlParams)
            throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(uri);
        builder = builder.addParameter(TOKEN, AccessTokenUtil.getToken());
        uri = builder.build();
        JSONObject ret = JSON.parseObject(sendPost(uri, encoding, trxData, urlParams));

        int retCd = ret.getIntValue(ERROR_CODE);
        // token超时,获取新的TOKEN重新发起
        if (TOKEN_TIMEOUNT == retCd) {
            logger.info("token timeout,get new,and send post again");
            builder.setParameter(TOKEN, AccessTokenUtil.getNewToken());
            uri = builder.build();
            ret = JSON.parseObject(sendPost(uri, encoding, trxData, urlParams));
        }
        return ret;
    }
}
