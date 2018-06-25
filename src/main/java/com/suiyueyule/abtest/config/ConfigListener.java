package com.suiyueyule.abtest.config;

import com.suiyueyule.abtest.core.ABTestManager;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
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
        try {

            logger.debug(String.format("onFileChange %s", file.getAbsolutePath()));

            if (file.getAbsolutePath().equals(ConfigManage.filename)) {

                //加载新的配置文件
                ConfigManage configManage = ConfigManage.load(file);

                //刷新分流配置
                ABTestManager.instance().refreshConfiguration(configManage.abTestConfig);
            }

        } catch (Exception e) {
            logger.error(e);
        }
    }

}

