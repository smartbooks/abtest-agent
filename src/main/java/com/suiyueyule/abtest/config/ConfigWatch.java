package com.suiyueyule.abtest.config;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * 配置文件监听器
 */
public class ConfigWatch {

    private final static Logger logger = LogManager.getLogger(ConfigWatch.class);
    private FileAlterationMonitor monitor;
    private long interval = 5L;

    public ConfigWatch() {
        FileAlterationObserver observer = new FileAlterationObserver(ConfigManage.RECOMMEND_CONF_DIR);
        observer.addListener(new ConfigListener());
        monitor = new FileAlterationMonitor(TimeUnit.SECONDS.toSeconds(interval), observer);
    }

    public void start() {
        try {
            monitor.start();
        } catch (Exception e) {
            logger.error("配置文件监控器启动失败", e);
        }
    }

    public void stop() {
        try {
            monitor.stop();
        } catch (Exception e) {
            logger.error("配置文件监控器停止失败", e);
        }
    }
}

