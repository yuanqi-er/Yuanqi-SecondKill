package com.yuanqi.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Util {
    public final static String md5Key = "yuanqi";

    /**
     *  把密码进行加密
     * @param text 明文
     * @param key 密钥
     * @return 密文
     */
    public static String md5(String text, String key) throws Exception {
        //加密后的字符串
        String encoderStr = DigestUtils.md5Hex(text + key);
        System.out.println("MD5加密后的字符串为：" + encoderStr);
        return encoderStr;
    }

    /**
     *
     * @param text 明文
     * @param key 密钥
     * @param md5 密文
     * @return true/false
     */
    public static boolean verify(String text, String key, String md5) throws Exception {
        //根据传入的密钥进行验证
        String md5Text = md5(text, key);
        if (md5Text.equalsIgnoreCase(md5)) {
            System.out.println("MD5验证通过");
            return true;
        }
        return false;
    }


}
