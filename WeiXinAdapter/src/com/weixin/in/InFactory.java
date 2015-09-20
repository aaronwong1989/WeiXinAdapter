/**************************************************************************
 * Copyright (c) 2011-2012 信雅达系统工程有限公司. All rights reserved.
 *
 * 项目名称：微客平台 版权说明：本软件属信雅达信息系统工程有限公司所有，在未获信雅达系统工程有限公司正式授权 情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知 识产权保护的内容。
 ***************************************************************************/
package com.weixin.in;

import com.util.xml.AdvanceXStream;
import com.util.xml.converter.JavaObjectConverter;
import com.weixin.in.event.Click;
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
 * 上行消息&事件转换工厂
 */
public class InFactory {
    private static InFactory instance = new InFactory();

    /**
     * 获取实例
     *
     * @return
     */
    public static InFactory getInstance() {
        return instance;
    }

    /** xml解析器 */
    private AdvanceXStream advanceXStream = new AdvanceXStream();

    private InFactory() {
        // 对象转换器
        advanceXStream.registerConverter(new JavaObjectConverter());
        // 基类
        advanceXStream.registerParserClass(InBase.class);
        // 普通消息类
        advanceXStream.registerParserClass(Text.class, Image.class, Link.class, Video.class, Voice.class, MsgLocation.class);
        // 事件类
        advanceXStream.registerParserClass(Click.class, MsgLocation.class, Scan.class, Subscribe.class, UnSubscribe.class, View.class);
    }

    /**
     * 消息体XML转换成对象<br>
     * ＜xml＞替换为＜MsgType＞
     *
     * @param xml
     * @return
     */
    public InBase fromXml(String xml) {
        xml = xml.trim();
        InBase base = (InBase) advanceXStream.fromXml(xml);
        // 如果MsgType=event将顶级XML标签替换为<Event>的值,
        // 否则替换为 <MsgType>的值
        String replace = base.getMsgType();
        if ("event".equalsIgnoreCase(replace)) {
            replace = base.getEvent();
        }
        xml = xml.replaceAll("^<xml>", "<" + replace + ">");
        xml = xml.replaceAll("</xml>$", "</" + replace + ">");
        return (InBase) advanceXStream.fromXml(xml);
    }

    /**
     * 消息体转换成XML格式字符串
     *
     * @param InBase
     * @return
     */
    public String toXml(InBase in) {
        String xml = advanceXStream.toArributeXML(in);
        StringBuffer xmlBuffer = new StringBuffer("<xml>");
        xmlBuffer.append('\n');
        xmlBuffer.append(xml);
        xmlBuffer.append("</xml>");
        return xmlBuffer.toString();
    }
}
