package com.sunup.education.tools;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringTools {

    public static final StringBuffer BUFFER = new StringBuffer();
    public static final StringBuilder BUILDER = new StringBuilder();

    /**
     * 生成随机字符串
     *
     * @return
     */
    public static String generateRandomString() {
        BUFFER.delete(0, BUFFER.length());
        final String s = "poiuytrewqasdfghjklmnbvcxz0987654321";
        final int slen = s.length() - 1;
        for (int i = 0, len = 16; i < len; i++) {
            BUFFER.append(s.charAt(RandomTools.randInt(0, slen)));
        }
        return BUFFER.toString();
    }

    /**
     * 对象拼接
     *
     * @param params
     * @return StringBuilder
     */
    public static StringBuilder builderAppend(Object... params) {
        BUFFER.delete(0, BUFFER.length());
        for (int i = 0, len = params.length; i < len; i++) {
            BUFFER.append(params[i]);
        }
        return BUILDER;
    }

    /**
     * 对象拼接
     *
     * @param params
     * @return String
     */
    public static String builderAppendToString(Object... params) {
        return builderAppend(params).toString();
    }

    /**
     * 对象拼接
     *
     * @param params
     * @return StringBuffer
     */
    public static StringBuffer bufferAppend(Object... params) {
        BUFFER.delete(0, BUFFER.length());
        for (int i = 0, len = params.length; i < len; i++) {
            BUFFER.append(params[i]);
        }

        return BUFFER;
    }

    /**
     * 对象拼接
     *
     * @param params
     * @return String
     */
    public static String bufferAppendToString(Object... params) {
        return bufferAppend(params).toString();
    }


    public static void main(String[] args) {
        log.debug(generateRandomString());
    }
}
