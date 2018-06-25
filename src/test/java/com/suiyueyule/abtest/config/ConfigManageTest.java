package com.suiyueyule.abtest.config;


import com.suiyueyule.abtest.action.AbForwardAction;
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

        c.abTestConfig = AbForwardAction.genericExperimentConfiguration();

        return c;
    }

}
