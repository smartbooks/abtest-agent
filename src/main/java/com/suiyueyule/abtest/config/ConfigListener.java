package com.suiyueyule.abtest.config;

import com.suiyueyule.abtest.core.ABTestManager;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * 配置文件监听器观察者，当配置文件发生改变时，自动重新加载配置文件到内存中
 */
public class ConfigListener extends FileAlterationListenerAdaptor {

    private final static Logger logger = LogManager.getLogger(ConfigListener.class);

    @Override
    public void onFileChange(File file) {
        logger.debug(String.format("onFileChange %s", file));
        refreshConfig(file);
    }

    @Override
    public void onFileCreate(File file) {
        logger.debug(String.format("onFileCreate %s", file));
        refreshConfig(file);
    }

    @Override
    public void onFileDelete(File file) {
        logger.debug(String.format("onFileDelete %s", file));
    }

    @Override
    public void onDirectoryChange(File directory) {
        logger.debug(String.format("onDirectoryChange %s", directory));
    }

    @Override
    public void onDirectoryCreate(File directory) {
        logger.debug(String.format("onDirectoryCreate %s", directory));
    }

    @Override
    public void onDirectoryDelete(File directory) {
        logger.debug(String.format("onDirectoryDelete %s", directory));
    }

    @Override
    public void onStart(FileAlterationObserver observer) {
        //logger.debug(String.format("onStart %s", observer));
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        //logger.debug(String.format("onStop %s", observer));
    }

    private void refreshConfig(File file) {
        try {

            if (file.getAbsolutePath().equals(ConfigManage.filename)) {

                //加载新的配置文件
                ConfigManage configManage = ConfigManage.load(file);

                //刷新分流配置
                ABTestManager.instance().refreshConfiguration(configManage.abTestConfig);
            }

        } catch (Exception e) {
            logger.error("刷新配置文件到内存失败", e);
        }
    }
}

