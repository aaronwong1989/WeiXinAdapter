/**
 * �Թ���ƽ̨���͸������˺ŵ���Ϣ�ӽ���ʾ������.
 *
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

package com.weixin.mp.aes;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * SHA1 class
 *
 * ���㹫��ƽ̨����Ϣǩ���ӿ�.
 */
class SHA1 {

    /**
     * ��SHA1�㷨���ɰ�ȫǩ��
     *
     * @param token
     *            Ʊ��
     * @param timestamp
     *            ʱ���
     * @param nonce
     *            ����ַ���
     * @param encrypt
     *            ����
     * @return ��ȫǩ��
     * @throws AesException
     */
    public static String getSHA1(String token, String timestamp, String nonce, String encrypt) throws AesException {
        try {
            String[] array = new String[] { token, timestamp, nonce, encrypt };
            StringBuffer sb = new StringBuffer();
            // �ַ�������
            Arrays.sort(array);
            for (int i = 0; i < 4; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();
            // SHA1ǩ������
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (byte element : digest) {
                shaHex = Integer.toHexString(element & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AesException(AesException.ComputeSignatureError);
        }
    }
}
