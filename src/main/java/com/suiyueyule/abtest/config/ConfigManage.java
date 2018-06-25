package com.suiyueyule.abtest.config;

import com.suiyueyule.abtest.core.ExperimentConfiguration;
import com.suiyueyule.abtest.util.FileUtils;
import com.suiyueyule.abtest.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ConfigManage {

    private final static Logger logger = LogManager.getLogger(ConfigManage.class);

    /**
     * 默认配置文件
     */
    public static String filename;

    /**
     * 配置文件目录
     */
    public static String RECOMMEND_CONF_DIR;

    /**
     * 配置对象实例
     */
    public static ConfigManage Instance = null;

    static {
        RECOMMEND_CONF_DIR = System.getenv("RECOMMEND_CONF_DIR");
        if (null == RECOMMEND_CONF_DIR || RECOMMEND_CONF_DIR.equals(StringUtils.STRING_EMPTY)) {
            RECOMMEND_CONF_DIR = System.getProperty("user.dir");
        }
        filename = String.format("%sdefault.yaml", RECOMMEND_CONF_DIR);
    }

    /**
     * 加载配置
     *
     * @param file
     * @return
     */
    public synchronized static ConfigManage load(File file) {
        Yaml yaml = new Yaml();

        try {
            Instance = yaml.load(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            logger.error(String.format("序列化配置文件失败", file), e);
        }

        return Instance;
    }

    /**
     * 加载配置
     *
     * @return
     */
    public synchronized static ConfigManage load() {
        Yaml yaml = new Yaml();

        File confFile = new File(filename);

        try {
            Instance = yaml.load(new FileInputStream(confFile));
        } catch (FileNotFoundException e) {
            logger.error(String.format("序列化配置文件失败", confFile), e);
        }

        return Instance;
    }

    /**
     * 保存配置
     *
     * @param configManage
     */
    public synchronized static void save(ConfigManage configManage) {
        Yaml yaml = new Yaml();

        String yamlText = yaml.dump(configManage);

        FileUtils.writeFile(new File(filename), yamlText);
    }

    public synchronized static void save() {
        save(Instance);
    }

    /**
     * AB分流测试配置
     */
    public ExperimentConfiguration abTestConfig = null;

    //其他配置在此处继续添加

    @Override
    public String toString() {
        return new Yaml().dump(this);
    }

}
