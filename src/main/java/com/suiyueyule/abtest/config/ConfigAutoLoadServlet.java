package com.suiyueyule.abtest.config;

import com.suiyueyule.abtest.core.ABTestManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 配置文件随着WEB服务器的启动自动加载到内存中
 */
public class ConfigAutoLoadServlet extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(ConfigAutoLoadServlet.class);

    private ConfigWatch configWatch;

    @Override
    public void init() throws ServletException {
        super.init();

        configWatch = new ConfigWatch();
        configWatch.start();
        logger.debug("配置文件自动刷新服务启动");

        //加载配置文件
        ConfigManage.load();

        if (null == ConfigManage.Instance) {
            logger.error(String.format("配置文件加载失败，必须修复后才能正常运行，配置文件位置:%s", ConfigManage.filename));
        } else {
            //刷新分流配置
            ABTestManager.instance().refreshConfiguration(ConfigManage.Instance.abTestConfig);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        configWatch.stop();
        logger.debug("配置文件自动刷新服务停止");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getRequestURI().equals("/conf/f5")) {
            ConfigManage.load();
            if (ConfigManage.Instance != null) {
                ABTestManager.instance().refreshConfiguration(ConfigManage.Instance.abTestConfig);
            }
        }
    }
}
