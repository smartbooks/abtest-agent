package com.suiyueyule.abtest.config;

import com.suiyueyule.abtest.core.ABTestManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 配置文件随着WEB服务器的启动自动加载到内存中
 */
public class ConfigAutoLoadServlet extends HttpServlet {

    private ConfigWatch configWatch;

    @Override
    public void init() throws ServletException {
        super.init();

        //加载配置文件
        ConfigManage configManage = ConfigManage.load();

        //刷新分流配置
        ABTestManager.instance().refreshConfiguration(configManage.abTestConfig);

        configWatch = new ConfigWatch();

        configWatch.start();
    }

    @Override
    public void destroy() {
        super.destroy();
        configWatch.stop();
    }
}
