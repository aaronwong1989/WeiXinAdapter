/**************************************************************************
 * Copyright (c) 2011-2012 ���Ŵ�ϵͳ�������޹�˾. All rights reserved.
 *
 * ��Ŀ���ƣ�΢��ƽ̨ ��Ȩ˵��������������Ŵ���Ϣϵͳ�������޹�˾���У���δ�����Ŵ�ϵͳ�������޹�˾��ʽ��Ȩ ����£��κ���ҵ�͸��ˣ����ܻ�ȡ���Ķ�����װ������������漰���κ���֪ ʶ��Ȩ���������ݡ�
 ***************************************************************************/
package weixin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.weixin.in.InFactory;
import com.weixin.in.message.MsgLocation;
import com.weixin.in.message.Text;

/**
 * anytest
 *
 * @author <a href="mailto:huang.zh@sunyard.com">huang.zh</a>
 * @version $Id: TMain.java, v 0.1 2014��12��27�� ����2:51:33 rdpc1384 Exp $
 * @since 2.0
 */
public class AnyTest {
    static Logger logger = LogManager.getLogger(AnyTest.class);

    public static void main(String[] args) {
        Text text = new Text();
        text.setContent("hello world,�������");
        text.setCreateTime(System.currentTimeMillis());
        text.setFromUserName("fromUserName");
        text.setToUserName("toUserName");
        text.setMsgId(1L);
        String xml = InFactory.getInstance().toXml(text);
        System.out.println(xml);
        System.out.println(InFactory.getInstance().fromXml(xml).toString());

        MsgLocation location = new MsgLocation();
        location.setCreateTime(System.currentTimeMillis());
        location.setFromUserName("fromUserName");
        location.setToUserName("toUserName");
        location.setMsgId(2L);
        location.setLocation_X(235.11);
        location.setLocation_Y(239.11);
        xml = InFactory.getInstance().toXml(location);
        System.out.println(xml);
        System.out.println(InFactory.getInstance().fromXml(xml).toString());

        logger.info("1", "....");
    }
}
