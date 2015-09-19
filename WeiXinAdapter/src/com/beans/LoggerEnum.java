/**
 * LoggerNames.java<br>
 *
 * @author huangzhong_hui@163.com
 */
package com.beans;

/**
 * 日志appder名
 *
 * @author huangzhong_hui@163.com
 * @version 2014年12月20日
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
