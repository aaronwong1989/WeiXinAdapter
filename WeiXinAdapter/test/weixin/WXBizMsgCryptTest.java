package weixin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.weixin.mp.aes.AesException;
import com.weixin.mp.aes.WXBizMsgCrypt;

public class WXBizMsgCryptTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    String afterAesEncrypt  = "jn1L23DB+6ELqJ+6bruv21Y6MD7KeIfP82D6gU39rmkgczbWwt5+3bnyg5K55bgVtVzd832WzZGMhkP72vVOfg==";
    String afterAesEncrypt2 = "jn1L23DB+6ELqJ+6bruv23M2GmYfkv0xBh2h+XTBOKVKcgDFHle6gqcZ1cZrk3e1qjPQ1F4RsLWzQRG9udbKWesxlkupqcEcW7ZQweImX9+wLMa0GaUzpkycA8+IamDBxn5loLgZpnS7fVAbExOkK5DYHBmv5tptA9tklE/fTIILHR8HLXa5nQvFb3tYPKAlHF3rtTeayNf0QuM+UW/wM9enGIDIJHF7CLHiDNAYxr+r+OrJCmPQyTy8cVWlu9iSvOHPT/77bZqJucQHQ04sq7KZI27OcqpQNSto2OdHCoTccjggX5Z9Mma0nMJBU+jLKJ38YB1fBIz+vBzsYjrTmFQ44YfeEuZ+xRTQwr92vhA9OxchWVINGC50qE/6lmkwWTwGX9wtQpsJKhP+oS7rvTY8+VdzETdfakjkwQ5/Xka042OlUb1/slTwo4RscuQ+RdxSGvDahxAJ6+EAjLt9d8igHngxIbf6YyqqROxuxqIeIch3CssH/LqRs+iAcILvApYZckqmA7FNERspKA5f8GoJ9sv8xmGvZ9Yrf57cExWtnX8aCMMaBropU/1k+hKP5LVdzbWCG0hGwx/dQudYR/eXp3P0XxjlFiy+9DMlaFExWUZQDajPkdPrEeOwofJb";
    String appId            = "wxb11529c136998cb6";
    String encodingAesKey   = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG";
    String nonce            = "xxxxxx";
    String randomStr        = "aaaabbbbccccdddd";
    String replyMsg         = "��������abcd123";

    String replyMsg2        = "<xml><ToUserName><![CDATA[oia2Tj��������jewbmiOUlr6X-1crbLOvLw]]></ToUserName><FromUserName><![CDATA[gh_7f083739789a]]></FromUserName><CreateTime>1407743423</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[eYJ1MbwPRJtOvIEabaxHs7TX2D-HV71s79GUxqdUkjm6Gs2Ed1KF3ulAOA9H1xG0]]></MediaId><Title><![CDATA[testCallBackReplyVideo]]></Title><Description><![CDATA[testCallBackReplyVideo]]></Description></Video></xml>";
    String timestamp        = "1409304348";

    String token            = "pamtest";

    String xmlFormat        = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAesEncrypt() {
        try {
            WXBizMsgCrypt pc = new WXBizMsgCrypt(this.token, this.encodingAesKey, this.appId);
            assertEquals(this.afterAesEncrypt, pc.encrypt(this.randomStr, this.replyMsg));
        } catch (AesException e) {
            e.printStackTrace();
            fail("no�쳣");
        }
    }

    @Test
    public void testAesEncrypt2() {
        try {
            WXBizMsgCrypt pc = new WXBizMsgCrypt(this.token, this.encodingAesKey, this.appId);
            assertEquals(this.afterAesEncrypt2, pc.encrypt(this.randomStr, this.replyMsg2));

        } catch (AesException e) {
            e.printStackTrace();
            fail("no�쳣");
        }
    }

    @Test
    public void testIllegalAesKey() {
        try {
            new WXBizMsgCrypt(this.token, "abcde", this.appId);
        } catch (AesException e) {
            assertEquals(AesException.IllegalAesKey, e.getCode());
            return;
        }
        fail("�������̲��׳��쳣������");
    }

    @Test
    public void testNormal() throws ParserConfigurationException, SAXException, IOException {
        try {
            WXBizMsgCrypt pc = new WXBizMsgCrypt(this.token, this.encodingAesKey, this.appId);
            String afterEncrpt = pc.encryptMsg(this.replyMsg, this.timestamp, this.nonce);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(afterEncrpt);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);

            Element root = document.getDocumentElement();
            NodeList nodelist1 = root.getElementsByTagName("Encrypt");
            NodeList nodelist2 = root.getElementsByTagName("MsgSignature");

            String encrypt = nodelist1.item(0).getTextContent();
            String msgSignature = nodelist2.item(0).getTextContent();
            String fromXML = String.format(this.xmlFormat, encrypt);

            // �������յ����ں�ƽ̨���͵���Ϣ
            String afterDecrpt = pc.decryptMsg(msgSignature, this.timestamp, this.nonce, fromXML);
            assertEquals(this.replyMsg, afterDecrpt);
        } catch (AesException e) {
            fail("�������̣���ô���׳��쳣�ˣ�����������");
        }
    }

    @Test
    public void testValidateSignatureError() throws ParserConfigurationException, SAXException, IOException {
        try {
            WXBizMsgCrypt pc = new WXBizMsgCrypt(this.token, this.encodingAesKey, this.appId);
            String afterEncrpt = pc.encryptMsg(this.replyMsg, this.timestamp, this.nonce);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(afterEncrpt);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);

            Element root = document.getDocumentElement();
            NodeList nodelist1 = root.getElementsByTagName("Encrypt");

            String encrypt = nodelist1.item(0).getTextContent();
            String fromXML = String.format(this.xmlFormat, encrypt);
            pc.decryptMsg("12345", this.timestamp, this.nonce, fromXML); // ����ǩ������
        } catch (AesException e) {
            assertEquals(AesException.ValidateSignatureError, e.getCode());
            return;
        }
        fail("�������̲��׳��쳣������");
    }

    @Test
    public void testVerifyUrl() throws AesException {
        WXBizMsgCrypt wxcpt = new WXBizMsgCrypt("QDG6eK", "jWmYm7qr5nMoAUwZRjGtBxmz3KA1tkAj3ykkR6q2B2C", "wx5823bf96d3bd56c7");
        String verifyMsgSig = "5c45ff5e21c57e6ad56bac8758b79b1d9ac89fd3";
        String timeStamp = "1409659589";
        String nonce = "263014780";
        String echoStr = "P9nAzCzyDtyTWESHep1vC5X9xho/qYX3Zpb4yKa9SKld1DsH3Iyt3tP3zNdtp+4RPcs8TgAE7OaBO+FZXvnaqQ==";
        wxcpt.verifyUrl(verifyMsgSig, timeStamp, nonce, echoStr);
        // ֻҪ���׳��쳣�ͺ�
    }
}
