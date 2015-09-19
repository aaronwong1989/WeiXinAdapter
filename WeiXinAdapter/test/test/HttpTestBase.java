/**
 * 2014年10月27日
 *
 * @Auther huangzhong_hui@163.com
 */
package test;

import java.io.IOException;
import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/**
 * http协议测试父类
 *
 * @author huangzhong_hui@163.com
 *
 */
public class HttpTestBase {
    protected static String                       encoding       = "UTF-8";
    protected static Logger                       logger         = LogManager.getLogger(HttpTestBase.class);
    static HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

    /**
     * 获取ClientHttpRequest
     *
     * @author huangzhong_hui@163.com
     * @param uri
     * @param httpMethod
     * @return
     * @throws IOException
     */
    public static ClientHttpRequest getRequest(URI uri, HttpMethod httpMethod) throws IOException {
        return HttpTestBase.requestFactory.createRequest(uri, httpMethod);
    }

    public HttpTestBase() {
        HttpTestBase.requestFactory.setConnectTimeout(10 * 1000);
        HttpTestBase.requestFactory.setReadTimeout(60 * 1000);
    }

}
