package com.cold.tutorial.soa;

import java.security.MessageDigest;

/**
 * @author hui.liao
 *         2016/1/19 17:00
 */
public class Hash {

    public static void testMD5() throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String str = "1234";

        byte[] bytes = md5.digest(str.getBytes("ISO-8859-1"));
    }

    public static void testSHA() throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("SHA");
        String str = "1234";

        byte[] bytes = md5.digest(str.getBytes("ISO-8859-1"));
    }

    public static void main(String[] args) throws Exception {
        testMD5();
    }
}
