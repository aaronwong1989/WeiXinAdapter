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
 * XStream增强版
 * */
public class AdvanceXStream {
    /**
     * 日志操作
     */
    private static final Logger logger         = LogManager.getLogger(AdvanceXStream.class);

    /**
     * 操作名称
     */
    private static final String OPERATION_NAME = "【XML解析】";

    /**
     * CDDATA修饰符开始
     *
     */
    private static final String PREFIX_CDATA   = "<![CDATA[";

    /**
     * CDDATA修饰符结束
     *
     */
    private static final String SUFFIX_CDATA   = "]]>";

    /**
     * 封装对象
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
     * 注册转换器
     *
     * @param converter
     */
    public void registerConverter(Converter converter) {

        this.xStream.registerConverter(converter);
    }

    /**
     * 注入解析对象，针对对象的XStreamAlias注解进行转换
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
     * 对象属性->xml
     *
     * <pre>
     *  与toXml不同的是，本方法只是将当前对象的所有属性变成xml格式，并拼成字符串格式
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

        // 遍历所有属性，转换成成xml标签格式
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
                logger.warn(OPERATION_NAME + ",属性转换成XML异常", e);
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
