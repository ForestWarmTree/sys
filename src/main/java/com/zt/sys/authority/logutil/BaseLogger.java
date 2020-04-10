package com.zt.sys.authority.logutil;

import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ  IDEA
 * User: 王传威
 * Date: 2020/4/8
 * Time: 9:33
 */
public abstract class BaseLogger {

    /** the project code of the logger */
    //private static String projectCode = "LogProject";


    public static void logerror(String msg) {
        LoggerFactory.getLogger(getClassName()).error(formatMessageTemplate(msg));
    }

    public static void logerror(String msg, Object... obj) {
        LoggerFactory.getLogger(getClassName()).error(formatMessageTemplate(msg), obj);
    }

    public static void logwarn(String msg) {
        if(LoggerFactory.getLogger(getClassName()).isWarnEnabled()) {
            LoggerFactory.getLogger(getClassName()).error(formatMessageTemplate(msg));
        }
    }

    public static void logwarn(String msg, Object... obj) {
        if(LoggerFactory.getLogger(getClassName()).isWarnEnabled()) {
            LoggerFactory.getLogger(getClassName()).error(formatMessageTemplate(msg), obj);
        }
    }

    public static void loginfo(String msg) {
        if(LoggerFactory.getLogger(getClassName()).isInfoEnabled()) {
            LoggerFactory.getLogger(getClassName()).info(formatMessageTemplate(msg));
        }
    }

    public static void loginfo(String msg, Object... obj) {
        if(LoggerFactory.getLogger(getClassName()).isInfoEnabled()) {
            LoggerFactory.getLogger(getClassName()).info(formatMessageTemplate(msg), obj);
        }
    }

    public static void logdebug(String msg) {
        if(LoggerFactory.getLogger(getClassName()).isDebugEnabled()) {
            LoggerFactory.getLogger(getClassName()).debug(formatMessageTemplate(msg));
        }
    }

    public static void logdebug(String msg, Object... obj) {
        if(LoggerFactory.getLogger(getClassName()).isDebugEnabled()) {
            LoggerFactory.getLogger(getClassName()).debug(formatMessageTemplate(msg), obj);
        }
    }

    /**
     * 格式化日志消息字符串
     * @param messageTemplate
     * @return
     */
    public static String formatMessageTemplate(String messageTemplate) {
        messageTemplate = getMethodName()+"方法:"+messageTemplate;
        if(messageTemplate.length() > 5000) {
            messageTemplate = messageTemplate.substring(0,messageTemplate.length()-2001);
        }
        return messageTemplate;
    }

    // 获取调用 error,info,debug静态类的类名
    private static String getClassName() {
        return new SecurityManager() {
            public String getClassName() {
                return getClassContext()[3].getName();
            }
        }.getClassName();
    }

    // 获取上级调用方法名
    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[4].getMethodName();
    }
}
