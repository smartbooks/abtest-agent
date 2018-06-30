package com.suiyueyule.abtest.config;

import org.junit.Test;

import java.util.UUID;

public class ConfigWatchTest {

    @Test
    public void configWatchTest() {

        ConfigWatch configWatch = new ConfigWatch();

        configWatch.start();

        while (true) {
            try {
                Thread.sleep(5000L);
                System.out.println("*************");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void longParseTest() {

        String md5 = "C12E01F2A13FF5587E1E9E4AEDB8242D";

        for (int i = 0; i < 4; i++) {

            int offset = i * 8;

            String sub = md5.substring(offset, offset + 8);

            long sub16 = Long.parseLong(sub, 16);

            System.out.println(String.format("long:%s offset:%s mod_int:%s int_max:%s",
                    sub16,
                    sub16 / Integer.MAX_VALUE,
                    sub16 % Integer.MAX_VALUE,
                    Integer.MAX_VALUE));
        }
    }

    public static String[] chars = {"a", "b", "c", "d", "e", "f", "g", "h",

            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",

            "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",

            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",

            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",

            "U", "V", "W", "X", "Y", "Z"
    };

    public static String build(String url) {
        if (url == null) {
            return null;
        }
        //先得到url的32个字符的md5码
        String md5 = "C12E01F2A13FF5587E1E9E4AEDB8242D";

        //将32个字符的md5码分成4段处理，每段8个字符
        for (int i = 0; i < 4; i++) {

            int offset = i * 8;

            String sub = md5.substring(offset, offset + 8);

            long sub16 = Long.parseLong(sub, 16); //将sub当作一个16进制的数，转成long

            // & 0X3FFFFFFF，去掉最前面的2位，只留下30位
            sub16 &= 0X3FFFFFFF;

            StringBuilder sb = new StringBuilder();
            //将剩下的30位分6段处理，每段5位
            for (int j = 0; j < 6; j++) {
                //得到一个 <= 61的数字
                long t = sub16 & 0x0000003D;
                sb.append(chars[(int) t]);

                sub16 >>= 5;  //将sub16右移5位
            }

            System.out.println(sb.toString());
        }

        return null;
    }

    public static void main(String[] args) {
        //build("http://blog.csdn.net/is_zhoufeng/article/details/21494281");

    }
}
