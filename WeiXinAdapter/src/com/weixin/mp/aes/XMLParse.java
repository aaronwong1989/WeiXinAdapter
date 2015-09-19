/**
 * �Թ���ƽ̨���͸������˺ŵ���Ϣ�ӽ���ʾ������.
 *
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

package com.weixin.mp.aes;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * XMLParse class
 *
 * �ṩ��ȡ��Ϣ��ʽ�е����ļ����ɻظ���Ϣ��ʽ�Ľӿ�.
 */
class XMLParse {

    /**
     * ��ȡ��xml���ݰ��еļ�����Ϣ
     *
     * @param xmltext
     *            ����ȡ��xml�ַ���
     * @return ��ȡ���ļ�����Ϣ�ַ���
     * @throws AesException
     */
    public static Object[] extract(String xmltext) throws AesException {
        Object[] result = new Object[3];
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(xmltext);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);

            Element root = document.getDocumentElement();
            NodeList nodelist1 = root.getElementsByTagName("Encrypt");
            NodeList nodelist2 = root.getElementsByTagName("ToUserName");
            result[0] = 0;
            result[1] = nodelist1.item(0).getTextContent();
            result[2] = nodelist2.item(0).getTextContent();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AesException(AesException.ParseXmlError);
        }
    }

    /**
     * ����xml��Ϣ
     *
     * @param encrypt
     *            ���ܺ����Ϣ����
     * @param signature
     *            ��ȫǩ��
     * @param timestamp
     *            ʱ���
     * @param nonce
     *            ����ַ���
     * @return ���ɵ�xml�ַ���
     */
    public static String generate(String encrypt, String signature, String timestamp, String nonce) {

        String format = "<xml>\n" + "<Encrypt><![CDATA[%1$s]]></Encrypt>\n" + "<MsgSignature><![CDATA[%2$s]]></MsgSignature>\n"
                + "<TimeStamp>%3$s</TimeStamp>\n" + "<Nonce><![CDATA[%4$s]]></Nonce>\n" + "</xml>";
        return String.format(format, encrypt, signature, timestamp, nonce);

    }
}
