package com.suiyueyule.abtest.config;

import com.suiyueyule.abtest.cache.RedisConfig;
import com.suiyueyule.abtest.core.Bucket;
import com.suiyueyule.abtest.core.ExperimentConfiguration;
import com.suiyueyule.abtest.core.Layer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 一个单元测试用例执行顺序为： @BeforeClass –> @Before –> @Test –> @After –> @AfterClass
 * 每一个测试方法的调用顺序为： @Before –> @Test –> @After
 */
public class ConfigManageTest {

    @BeforeClass
    public static void init() {
        System.out.println("我是打酱油的init");
    }

    @Test
    public void loadTest() {

        ConfigManage.load();

        System.out.println(ConfigManage.Instance);

        ConfigManage.save();

    }

    @Test
    public void saveTest() {

        ConfigManage.save(genericConfigManage());

    }

    @AfterClass
    public static void close() {
        System.out.println("我是打酱油的close");
    }

    @Ignore
    public void ignore() {
        System.out.println("我是打酱油的ignore");
    }


    public static ConfigManage genericConfigManage() {
        ConfigManage c = new ConfigManage();

        c.abTestConfig = genericExperimentConfiguration();

        c.recommendRedisConfig = new RedisConfig();
        c.recommendRedisConfig.host = "localhost";
        c.recommendRedisConfig.connectionTimeout = 1200;
        c.recommendRedisConfig.auth = "";
        c.recommendRedisConfig.dbIndex = 0;
        c.recommendRedisConfig.port = 6379;
        c.recommendRedisConfig.useSSL = false;

        return c;
    }

    public static ExperimentConfiguration genericExperimentConfiguration() {
        ExperimentConfiguration conf = new ExperimentConfiguration();

        {
            Layer ui = new Layer();
            ui.bucketSize = 2L;
            ui.factor = "RECOMMEND_ITEM";
            ui.bucketList = new Bucket[2];
            {
                Bucket b = new Bucket();
                b.bucketList.add(0L);
                b.plan = "201806A";
                b.paramList.put("PAMA", "000000");
                b.paramList.put("PAMB", "000001");
                b.classTag = "com.suiyueyule.abtest.algorithm.impl.DefaultRecommendItemServiceImpl";
                ui.bucketList[0] = b;
            }
            {
                Bucket b = new Bucket();
                b.bucketList.add(1L);
                b.plan = "201806B";
                b.paramList.put("PAMA", "000000");
                b.paramList.put("PAMB", "000001");
                b.classTag = "com.suiyueyule.abtest.algorithm.impl.CFRecommendItemServiceImpl";
                ui.bucketList[1] = b;
            }

            conf.layers.put("RECOMMEND_ITEM", ui);
        }

        {
            Layer query = new Layer();

            query.bucketSize = 2L;
            query.factor = "RECOMMEND_ITEM_RELEVANT";
            query.bucketList = new Bucket[2];
            {
                Bucket b = new Bucket();
                b.bucketList.add(0L);
                b.plan = "201806A";
                b.paramList.put("PAMA", "000000");
                b.paramList.put("PAMB", "000001");
                b.classTag = "com.suiyueyule.abtest.algorithm.impl.DefaultRecommendItemRelevantServiceImpl";
                query.bucketList[0] = b;
            }
            {
                Bucket b = new Bucket();
                b.bucketList.add(1L);
                b.plan = "201806B";
                b.paramList.put("PAMA", "000000");
                b.paramList.put("PAMB", "000001");
                b.classTag = "com.suiyueyule.abtest.algorithm.impl.CFRecommendItemRelevantServiceImpl";
                query.bucketList[1] = b;
            }

            conf.layers.put("RECOMMEND_ITEM_RELEVANT", query);
        }

        return conf;
    }

}
