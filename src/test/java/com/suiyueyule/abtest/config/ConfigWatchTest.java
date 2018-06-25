package com.suiyueyule.abtest.config;

import org.junit.Test;

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

}
