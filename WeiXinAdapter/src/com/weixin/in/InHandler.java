/**
 * InHandler.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.weixin.in;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.beans.HttpUtil;
import com.beans.LoggerEnum;
import com.beans.Settings;
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
 * 上行消息及事件处理器
 *
 * @author huangzhong_hui@163.com
 * @version 2014年12月28日
 */
public class InHandler {
    /**
     * 消息&事件异步处理类<br>
     * 处理后异步调用客服消息接口响应或者不响应
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月28日
     */
    class AsynHandler implements Runnable {
        private InBase in;

        public AsynHandler(InBase in) {
            this.in = in;
        }

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            // TODO 以下两个方法一般二选一
            toService();
            doService();
        }

        /**
         * 由系统自己处理
         *
         * @author huangzhong_hui@163.com
         * @version 2014年12月28日
         */
        private void doService() {
            // TODO 调用各自消息类型的服务类处理
            LOGGER.info("doService");
        }

        /**
         * 调用其它系统处理
         *
         * @author huangzhong_hui@163.com
         * @version 2014年12月28日
         */
        private void toService() {
            // TODO 可能需要的报文转换
            String trxdata = InFactory.getInstance().toXml(in);
            try {
                HttpUtil.sendPostWithToken(url, null, trxdata, null);
            } catch (URISyntaxException e) {
                ERRLOGGER.error("URL有误请检查settings.properties", e);
            } catch (IOException e) {
                ERRLOGGER.error(e.getMessage(), e);
            }
        }
    }

    private static int                      aliveTime;
    private static int                      corePoolSize;
    private static final Logger             ERRLOGGER = LogManager.getLogger(LoggerEnum.gateway_in);
    /** 消息及事件处理线程池 */
    private static ThreadPoolExecutor       inPool;
    private static InHandler                instacne  = new InHandler();
    private static final Logger             LOGGER    = LogManager.getLogger(InHandler.class);
    private static int                      quenceSize;
    private static RejectedExecutionHandler rejectedHandler;

    private static String url;

    public static InHandler getInstance() {
        return instacne;
    }

    private InHandler() {
        corePoolSize = Settings.getIntValue("settings", "InCorePoolSize");
        aliveTime = Settings.getIntValue("settings", "InKeepAliveTime");
        quenceSize = Settings.getIntValue("settings", "InQueueSize");
        // 线程池满负荷的拒绝处理策略:直接抛出RejectedExecutionException
        rejectedHandler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                inPool.setCorePoolSize((int) (corePoolSize * 1.2));
                inPool.setMaximumPoolSize(inPool.getCorePoolSize() * 2);
                executor.execute(r);
            }
        };
        inPool = new ThreadPoolExecutor(corePoolSize, corePoolSize * 2, aliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(quenceSize),
                rejectedHandler);
        url = Settings.getStringValue("settings", "local_service_url");
    }

    /**
     * 点击事件
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月28日
     * @param response
     * @param click
     * @throws IOException
     */
    public void doClick(HttpServletResponse response, Click click) throws IOException {
        long start = System.currentTimeMillis();
        LOGGER.info("点击事件doClick()处理,开始...");
        // 同步空响应
        response.getWriter().write("");
        // 异步处理
        inPool.execute(new AsynHandler(click));
        LOGGER.info("点击事件doClick()处理,结束,耗时{}ms", System.currentTimeMillis() - start);

    }

    /**
     * 上送位置事件
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月28日
     * @param response
     * @param in
     */
    public void doEvtLocation(HttpServletResponse response, EvtLocation in) {
        long start = System.currentTimeMillis();
        LOGGER.info("上送位置事件doEvtLocation()处理,开始...");
        // TODO
        LOGGER.info("上送位置事件doEvtLocation()处理,结束,耗时{}ms", System.currentTimeMillis() - start);

    }

    /**
     * 图片消息
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月28日
     * @param response
     * @param in
     */
    public void doImage(HttpServletResponse response, Image in) {
        long start = System.currentTimeMillis();
        LOGGER.info("图片消息doImage()处理,开始...");
        // TODO
        LOGGER.info("图片消息doImage()处理,结束,耗时{}ms", System.currentTimeMillis() - start);

    }

    /**
     * 链接消息
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月28日
     * @param response
     * @param in
     */
    public void doLink(HttpServletResponse response, Link in) {
        long start = System.currentTimeMillis();
        LOGGER.info("链接消息doLink()处理,开始...");
        // TODO
        LOGGER.info("链接消息doLink()处理,结束,耗时{}ms", System.currentTimeMillis() - start);
    }

    /**
     * 位置消息
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月28日
     * @param response
     * @param in
     */
    public void doMsgLocation(HttpServletResponse response, MsgLocation in) {
        long start = System.currentTimeMillis();
        LOGGER.info("位置消息doMsgLocation()处理,开始...");
        // TODO
        LOGGER.info("位置消息doMsgLocation()处理,结束,耗时{}ms", System.currentTimeMillis() - start);

    }

    /**
     * 二维码扫描事件
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月28日
     * @param response
     * @param in
     */
    public void doScan(HttpServletResponse response, Scan in) {
        long start = System.currentTimeMillis();
        LOGGER.info("二维码扫描事件doScan()处理,开始...");
        // TODO
        LOGGER.info("二维码扫描事件doScan()处理,结束,耗时{}ms", System.currentTimeMillis() - start);
    }

    /**
     * 关注事件
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月28日
     * @param response
     * @param in
     */
    public void doSubscribe(HttpServletResponse response, Subscribe in) {
        long start = System.currentTimeMillis();
        LOGGER.info("关注事件doSubscribe()处理,开始...");
        // TODO
        LOGGER.info("关注事件doSubscribe()处理,结束,耗时{}ms", System.currentTimeMillis() - start);
    }

    /**
     * 文本消息
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月28日
     * @param response
     * @param in
     */
    public void doText(HttpServletResponse response, Text in) {
        long start = System.currentTimeMillis();
        LOGGER.info("文本消息doText()处理,开始...");
        // TODO
        LOGGER.info("文本消息doText()处理,结束,耗时{}ms", System.currentTimeMillis() - start);
    }

    /**
     * 取消关注事件
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月28日
     * @param response
     * @param in
     */
    public void doUnSubscribe(HttpServletResponse response, UnSubscribe in) {
        long start = System.currentTimeMillis();
        LOGGER.info("取消关注事件doUnSubscribe()处理,开始...");
        // TODO
        LOGGER.info("取消关注事件doUnSubscribe()处理,结束,耗时{}ms", System.currentTimeMillis() - start);
    }

    /**
     * 视频消息
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月28日
     * @param response
     * @param in
     */
    public void doVideo(HttpServletResponse response, Video in) {
        long start = System.currentTimeMillis();
        LOGGER.info("视频消息doVideo()处理,开始...");
        // TODO
        LOGGER.info("视频消息doVideo()处理,结束,耗时{}ms", System.currentTimeMillis() - start);

    }

    /**
     * 点击链接按钮事件
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月28日
     * @param response
     * @param in
     */
    public void doView(HttpServletResponse response, View in) {
        long start = System.currentTimeMillis();
        LOGGER.info("点击链接按钮事件doView()处理,开始...");
        // TODO
        LOGGER.info("点击链接按钮事件doView()处理,结束,耗时{}ms", System.currentTimeMillis() - start);
    }

    /**
     * 语音消息
     *
     * @author huangzhong_hui@163.com
     * @version 2014年12月28日
     * @param response
     * @param in
     */
    public void doVoice(HttpServletResponse response, Voice in) {
        long start = System.currentTimeMillis();
        LOGGER.info("语音消息doVoice()处理,开始...");
        // TODO
        LOGGER.info("语音消息doVoice()处理,结束,耗时{}ms", System.currentTimeMillis() - start);

    }
}
