package com.suiyueyule.abtest.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MultiLayerExperiment {

    private static MessageDigest md5;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }

    private static long splitBucket(MessageDigest md5, long val, String shuffle) {
        String key = String.valueOf(val) + ((shuffle == null) ? "" : shuffle);
        byte[] ret = md5.digest(key.getBytes());
        String s = byteArrayToHex(ret);
        long hash = Long.parseUnsignedLong(s.substring(s.length() - 16, s.length() - 1), 16);
        if (hash < 0) {
            hash = hash * (-1);
        }
        return hash;
    }

    public static long getBucketNum(long cookie, long bucketSize, String factor) {
        long hashValue = splitBucket(md5, cookie, factor);
        return hashValue % bucketSize;
    }
}
