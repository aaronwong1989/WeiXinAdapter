/**************************************************************************
 * Copyright (c) 2011-2012 ���Ŵ�ϵͳ�������޹�˾. All rights reserved.
 *
 * ��Ŀ���ƣ�΢��ƽ̨ ��Ȩ˵��������������Ŵ���Ϣϵͳ�������޹�˾���У���δ�����Ŵ�ϵͳ�������޹�˾��ʽ��Ȩ ����£��κ���ҵ�͸��ˣ����ܻ�ȡ���Ķ�����װ������������漰���κ���֪ ʶ��Ȩ���������ݡ�
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
 * ���ز���
 *
 * @author <a href="mailto:huang.zh@sunyard.com">huang.zh</a>
 * @version $Id: GatewayTest.java, v 0.1 2014��12��27�� ����3:02:29 rdpc1384 Exp $
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
     * ģ����Ѷ�����ı���Ϣ
     *
     * @param request
     * @throws IOException
     */
    private void sendText(ClientHttpRequest request) throws IOException {
        Text text = new Text();
        text.setContent("hello world,����������");
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
