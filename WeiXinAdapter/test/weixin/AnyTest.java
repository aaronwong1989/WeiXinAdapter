/**************************************************************************
 * Copyright (c) 2011-2012 信雅达系统工程有限公司. All rights reserved.
 *
 * 项目名称：微客平台 版权说明：本软件属信雅达信息系统工程有限公司所有，在未获信雅达系统工程有限公司正式授权 情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知 识产权保护的内容。
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
 * @version $Id: TMain.java, v 0.1 2014年12月27日 下午2:51:33 rdpc1384 Exp $
 * @since 2.0
 */
public class AnyTest {
    static Logger logger = LogManager.getLogger(AnyTest.class);

    public static void main(String[] args) {
        Text text = new Text();
        text.setContent("hello world,世界你好");
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
