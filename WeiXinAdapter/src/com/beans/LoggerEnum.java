/**
 * LoggerNames.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.beans;

/**
 * ��־appder��
 *
 * @author huangzhong_hui@163.com
 * @version 2014��12��20��
 */
public enum LoggerEnum {
    errorLogger("errorLogger"), serviceLogger("serviceLogger");

    private final String value;

    LoggerEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
