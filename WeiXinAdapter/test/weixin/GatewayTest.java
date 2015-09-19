/**************************************************************************
 * Copyright (c) 2011-2012 信雅达系统工程有限公司. All rights reserved.
 *
 * 项目名称：微客平台 版权说明：本软件属信雅达信息系统工程有限公司所有，在未获信雅达系统工程有限公司正式授权 情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知 识产权保护的内容。
 ***************************************************************************/
package weixin;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import test.HttpTestBase;

import com.weixin.in.InFactory;
import com.weixin.in.message.Text;

/**
 * 网关测试
 *
 * @author <a href="mailto:huang.zh@sunyard.com">huang.zh</a>
 * @version $Id: GatewayTest.java, v 0.1 2014年12月27日 下午3:02:29 rdpc1384 Exp $
 * @since 2.0
 */
public class GatewayTest extends HttpTestBase {

    /**
     * Test method for {@link com.controller.GatewayController#gateway(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
     * .
     *
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public final void testGateway() throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder("http://127.0.0.1:8080/WeiXinAdapter/gateway");
        builder = builder.addParameter("channel", "1");
        URI uri = builder.build();
        ClientHttpRequest request = getRequest(uri, HttpMethod.POST);
        sendText(request);
    }

    /**
     * 模拟腾讯发送文本消息
     *
     * @param request
     * @throws IOException
     */
    private void sendText(ClientHttpRequest request) throws IOException {
        Text text = new Text();
        text.setContent("hello world,世界真美好");
        text.setCreateTime(System.currentTimeMillis());
        text.setFromUserName("fromUserName");
        text.setToUserName("toUserName");
        text.setMsgId(1L);

        String xml = InFactory.getInstance().toXml(text);
        logger.info("send post :\n {}", xml);
        OutputStream out = request.getBody();
        out.write(xml.getBytes(encoding));

        ClientHttpResponse response = request.execute();
        HttpStatus status = response.getStatusCode();
        String resp;
        if (HttpStatus.OK.value() == status.value()) {
            resp = IOUtils.toString(response.getBody(), encoding);
        } else {
            resp = "error";
        }
        logger.info("response status : {}", status.toString());
        logger.info("recive data : {}", resp);
        Assert.assertEquals(HttpStatus.OK, status);
    }
}
