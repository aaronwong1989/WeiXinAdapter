package com.util.xml;

import java.io.Writer;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

/**
 * XStream��ǿ��
 * */
public class AdvanceXStream {
    /**
     * ��־����
     */
    private static final Logger logger         = LogManager.getLogger(AdvanceXStream.class);

    /**
     * ��������
     */
    private static final String OPERATION_NAME = "��XML������";

    /**
     * CDDATA���η���ʼ
     *
     */
    private static final String PREFIX_CDATA   = "<![CDATA[";

    /**
     * CDDATA���η�����
     *
     */
    private static final String SUFFIX_CDATA   = "]]>";

    /**
     * ��װ����
     */
    private XStream             xStream;

    public AdvanceXStream() {

        super();
        this.xStream = new XStream(new DomDriver() {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (StringUtils.isBlank(text)) {
                            super.writeText(writer, text);
                        } else if (text.startsWith(AdvanceXStream.PREFIX_CDATA) && text.endsWith(AdvanceXStream.SUFFIX_CDATA)) {
                            writer.write(text);
                        } else {
                            writer.write(AdvanceXStream.PREFIX_CDATA + text + AdvanceXStream.SUFFIX_CDATA);
                        }
                    }
                };
            };
        });

    }

    /**
     * @param xStream
     */
    public AdvanceXStream(XStream xStream) {
        super();
        this.xStream = xStream;
    }

    /**
     * xml -> bean
     *
     * @param xml
     * @return
     */
    public Object fromXml(String xml) {

        if (StringUtils.isBlank(xml)) {
            return null;
        }

        return this.xStream.fromXML(xml);
    }

    /**
     * ע��ת����
     *
     * @param converter
     */
    public void registerConverter(Converter converter) {

        this.xStream.registerConverter(converter);
    }

    /**
     * ע�����������Զ����XStreamAliasע�����ת��
     *
     * @param objs
     */
    public void registerParserClass(Class<?>... clazs) {

        if (clazs == null || clazs.length <= 0) {

            return;
        }

        if (this.xStream == null) {

            return;
        }

        for (Class<?> clz : clazs) {

            XStreamAlias annotation = clz.getAnnotation(XStreamAlias.class);

            if (annotation != null && StringUtils.isNotBlank(annotation.value())) {

                this.xStream.alias(annotation.value(), clz);
            }

        }

    }

    /**
     * ��������->xml
     *
     * <pre>
     *  ��toXml��ͬ���ǣ�������ֻ�ǽ���ǰ������������Ա��xml��ʽ����ƴ���ַ�����ʽ
     * </pre>
     *
     * @param obj
     * @return
     */
    public String toArributeXML(Object obj) {

        if (obj == null) {
            return null;
        }

        StringBuilder xmlBuilder = new StringBuilder(100);
        List<Field> declaredFields = FieldsTool.getAllFields(obj);

        // �����������ԣ�ת���ɳ�xml��ǩ��ʽ
        for (Field field : declaredFields) {
            XStreamAlias annotation = field.getAnnotation(XStreamAlias.class);
            if (annotation == null) {
                continue;
            }
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value == null) {
                    continue;
                }
                this.xStream.alias(annotation.value(), field.getType());
                String xml = this.xStream.toXML(value).replace("__", "_");
                xmlBuilder.append("    ");
                xmlBuilder.append(xml);
                xmlBuilder.append('\n');

            } catch (Exception e) {
                logger.warn(OPERATION_NAME + ",����ת����XML�쳣", e);
            }
        }

        return xmlBuilder.toString();

    }

    /**
     * bean -> xml
     *
     * @param obj
     * @return
     */
    public String toXml(Object obj) {

        if (obj == null) {

            return null;
        }
        return this.xStream.toXML(obj);
    }

}
