/**************************************************************************
 * Copyright (c) 2011-2012 ���Ŵ�ϵͳ�������޹�˾. All rights reserved.
 *
 * ��Ŀ���ƣ�΢��ƽ̨ ��Ȩ˵��������������Ŵ���Ϣϵͳ�������޹�˾���У���δ�����Ŵ�ϵͳ�������޹�˾��ʽ��Ȩ ����£��κ���ҵ�͸��ˣ����ܻ�ȡ���Ķ�����װ������������漰���κ���֪ ʶ��Ȩ���������ݡ�
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
 * ������Ϣ&�¼�ת������
 */
public class InFactory {
    private static InFactory instance = new InFactory();

    /**
     * ��ȡʵ��
     *
     * @return
     */
    public static InFactory getInstance() {
        return instance;
    }

    /** xml������ */
    private AdvanceXStream advanceXStream = new AdvanceXStream();

    private InFactory() {
        // ����ת����
        advanceXStream.registerConverter(new JavaObjectConverter());
        // ����
        advanceXStream.registerParserClass(InBase.class);
        // ��ͨ��Ϣ��
        advanceXStream.registerParserClass(Text.class, Image.class, Link.class, Video.class, Voice.class, MsgLocation.class);
        // �¼���
        advanceXStream.registerParserClass(Click.class, MsgLocation.class, Scan.class, Subscribe.class, UnSubscribe.class, View.class);
    }

    /**
     * ��Ϣ��XMLת���ɶ���<br>
     * ��xml���滻Ϊ��MsgType��
     *
     * @param xml
     * @return
     */
    public InBase fromXml(String xml) {
        xml = xml.trim();
        InBase base = (InBase) advanceXStream.fromXml(xml);
        // ���MsgType=event������XML��ǩ�滻Ϊ<Event>��ֵ,
        // �����滻Ϊ <MsgType>��ֵ
        String replace = base.getMsgType();
        if ("event".equalsIgnoreCase(replace)) {
            replace = base.getEvent();
        }
        xml = xml.replaceAll("^<xml>", "<" + replace + ">");
        xml = xml.replaceAll("</xml>$", "</" + replace + ">");
        return (InBase) advanceXStream.fromXml(xml);
    }

    /**
     * ��Ϣ��ת����XML��ʽ�ַ���
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
