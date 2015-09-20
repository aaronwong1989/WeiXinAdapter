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
 * ������Ϣ���¼�������
 *
 * @author huangzhong_hui@163.com
 * @version 2014��12��28��
 */
public class InHandler {
    /**
     * ��Ϣ&�¼��첽������<br>
     * ������첽���ÿͷ���Ϣ�ӿ���Ӧ���߲���Ӧ
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��28��
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
            // TODO ������������һ���ѡһ
            toService();
            doService();
        }

        /**
         * ��ϵͳ�Լ�����
         *
         * @author huangzhong_hui@163.com
         * @version 2014��12��28��
         */
        private void doService() {
            // TODO ���ø�����Ϣ���͵ķ����ദ��
            LOGGER.info("doService");
        }

        /**
         * ��������ϵͳ����
         *
         * @author huangzhong_hui@163.com
         * @version 2014��12��28��
         */
        private void toService() {
            // TODO ������Ҫ�ı���ת��
            String trxdata = InFactory.getInstance().toXml(in);
            try {
                HttpUtil.sendPostWithToken(url, null, trxdata, null);
            } catch (URISyntaxException e) {
                ERRLOGGER.error("URL��������settings.properties", e);
            } catch (IOException e) {
                ERRLOGGER.error(e.getMessage(), e);
            }
        }
    }

    private static int                      aliveTime;
    private static int                      corePoolSize;
    private static final Logger             ERRLOGGER = LogManager.getLogger(LoggerEnum.gateway_in);
    /** ��Ϣ���¼������̳߳� */
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
        // �̳߳������ɵľܾ��������:ֱ���׳�RejectedExecutionException
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
     * ����¼�
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��28��
     * @param response
     * @param click
     * @throws IOException
     */
    public void doClick(HttpServletResponse response, Click click) throws IOException {
        long start = System.currentTimeMillis();
        LOGGER.info("����¼�doClick()����,��ʼ...");
        // ͬ������Ӧ
        response.getWriter().write("");
        // �첽����
        inPool.execute(new AsynHandler(click));
        LOGGER.info("����¼�doClick()����,����,��ʱ{}ms", System.currentTimeMillis() - start);

    }

    /**
     * ����λ���¼�
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��28��
     * @param response
     * @param in
     */
    public void doEvtLocation(HttpServletResponse response, EvtLocation in) {
        long start = System.currentTimeMillis();
        LOGGER.info("����λ���¼�doEvtLocation()����,��ʼ...");
        // TODO
        LOGGER.info("����λ���¼�doEvtLocation()����,����,��ʱ{}ms", System.currentTimeMillis() - start);

    }

    /**
     * ͼƬ��Ϣ
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��28��
     * @param response
     * @param in
     */
    public void doImage(HttpServletResponse response, Image in) {
        long start = System.currentTimeMillis();
        LOGGER.info("ͼƬ��ϢdoImage()����,��ʼ...");
        // TODO
        LOGGER.info("ͼƬ��ϢdoImage()����,����,��ʱ{}ms", System.currentTimeMillis() - start);

    }

    /**
     * ������Ϣ
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��28��
     * @param response
     * @param in
     */
    public void doLink(HttpServletResponse response, Link in) {
        long start = System.currentTimeMillis();
        LOGGER.info("������ϢdoLink()����,��ʼ...");
        // TODO
        LOGGER.info("������ϢdoLink()����,����,��ʱ{}ms", System.currentTimeMillis() - start);
    }

    /**
     * λ����Ϣ
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��28��
     * @param response
     * @param in
     */
    public void doMsgLocation(HttpServletResponse response, MsgLocation in) {
        long start = System.currentTimeMillis();
        LOGGER.info("λ����ϢdoMsgLocation()����,��ʼ...");
        // TODO
        LOGGER.info("λ����ϢdoMsgLocation()����,����,��ʱ{}ms", System.currentTimeMillis() - start);

    }

    /**
     * ��ά��ɨ���¼�
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��28��
     * @param response
     * @param in
     */
    public void doScan(HttpServletResponse response, Scan in) {
        long start = System.currentTimeMillis();
        LOGGER.info("��ά��ɨ���¼�doScan()����,��ʼ...");
        // TODO
        LOGGER.info("��ά��ɨ���¼�doScan()����,����,��ʱ{}ms", System.currentTimeMillis() - start);
    }

    /**
     * ��ע�¼�
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��28��
     * @param response
     * @param in
     */
    public void doSubscribe(HttpServletResponse response, Subscribe in) {
        long start = System.currentTimeMillis();
        LOGGER.info("��ע�¼�doSubscribe()����,��ʼ...");
        // TODO
        LOGGER.info("��ע�¼�doSubscribe()����,����,��ʱ{}ms", System.currentTimeMillis() - start);
    }

    /**
     * �ı���Ϣ
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��28��
     * @param response
     * @param in
     */
    public void doText(HttpServletResponse response, Text in) {
        long start = System.currentTimeMillis();
        LOGGER.info("�ı���ϢdoText()����,��ʼ...");
        // TODO
        LOGGER.info("�ı���ϢdoText()����,����,��ʱ{}ms", System.currentTimeMillis() - start);
    }

    /**
     * ȡ����ע�¼�
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��28��
     * @param response
     * @param in
     */
    public void doUnSubscribe(HttpServletResponse response, UnSubscribe in) {
        long start = System.currentTimeMillis();
        LOGGER.info("ȡ����ע�¼�doUnSubscribe()����,��ʼ...");
        // TODO
        LOGGER.info("ȡ����ע�¼�doUnSubscribe()����,����,��ʱ{}ms", System.currentTimeMillis() - start);
    }

    /**
     * ��Ƶ��Ϣ
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��28��
     * @param response
     * @param in
     */
    public void doVideo(HttpServletResponse response, Video in) {
        long start = System.currentTimeMillis();
        LOGGER.info("��Ƶ��ϢdoVideo()����,��ʼ...");
        // TODO
        LOGGER.info("��Ƶ��ϢdoVideo()����,����,��ʱ{}ms", System.currentTimeMillis() - start);

    }

    /**
     * ������Ӱ�ť�¼�
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��28��
     * @param response
     * @param in
     */
    public void doView(HttpServletResponse response, View in) {
        long start = System.currentTimeMillis();
        LOGGER.info("������Ӱ�ť�¼�doView()����,��ʼ...");
        // TODO
        LOGGER.info("������Ӱ�ť�¼�doView()����,����,��ʱ{}ms", System.currentTimeMillis() - start);
    }

    /**
     * ������Ϣ
     *
     * @author huangzhong_hui@163.com
     * @version 2014��12��28��
     * @param response
     * @param in
     */
    public void doVoice(HttpServletResponse response, Voice in) {
        long start = System.currentTimeMillis();
        LOGGER.info("������ϢdoVoice()����,��ʼ...");
        // TODO
        LOGGER.info("������ϢdoVoice()����,����,��ʱ{}ms", System.currentTimeMillis() - start);

    }
}
