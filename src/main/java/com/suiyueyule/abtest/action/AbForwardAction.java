package com.suiyueyule.abtest.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.suiyueyule.abtest.core.*;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AbForwardAction extends ActionSupport {


    public Map<String, Object> getJsonData() {
        return jsonData;
    }

    public void setJsonData(Map<String, Object> jsonData) {
        this.jsonData = jsonData;
    }

    private Map<String, Object> jsonData;

    public String query() {

        jsonData = new HashMap<>();

        HttpServletRequest request = ServletActionContext.getRequest();

        Map<String, String[]> param = request.getParameterMap();

        jsonData.put("data", param);

        return Action.SUCCESS;
    }

    public String hi() {

        jsonData = new HashMap<>();
        jsonData.put("data", null);

        jsonData.put("status", 0);
        jsonData.put("code", "0");
        jsonData.put("msg", "success");

        jsonData.put("time", System.currentTimeMillis() / 1000);

        return Action.SUCCESS;
    }

    public String exp() {

        jsonData = new HashMap<>();
        HttpServletRequest request = ServletActionContext.getRequest();

        String paramUid = request.getParameter("uid");
        long uid = 0;
        if (null != paramUid && paramUid.isEmpty() == false) {
            uid = Long.valueOf(paramUid);
        }

        //刷新分流配置
        ABTestManager.instance().refreshConfiguration(genericExperimentConfiguration());

        //实验策略配置
        ABTestFactor.ABTestResult layer1 = ABTestManager.instance().run(uid, "UI");
        ABTestFactor.ABTestResult layer2 = ABTestManager.instance().run(uid, "QUERY");
        ABTestFactor.ABTestResult layer3 = ABTestManager.instance().run(uid, "AD");

        jsonData.put("layer_1", layer1);
        jsonData.put("layer_2", layer2);
        jsonData.put("layer_3", layer3);

        jsonData.put("status", 0);
        jsonData.put("code", "0");
        jsonData.put("msg", "success");

        jsonData.put("time", System.currentTimeMillis() / 1000);

        return Action.SUCCESS;
    }

    private ExperimentConfiguration genericExperimentConfiguration() {
        ExperimentConfiguration conf = new ExperimentConfiguration();

        {
            Layer ui = new Layer();
            ui.bucketSize = 5L;
            ui.factor = "UI";
            ui.bucketList = new Bucket[2];
            {
                Bucket b = new Bucket();
                b.bucketList.add(0L);
                b.bucketList.add(1L);
                b.bucketList.add(2L);
                b.plan = "2018";
                b.paramList.put("UI_2018_01", "000000");
                b.paramList.put("UI_2018_02", "000001");
                ui.bucketList[0] = b;
            }
            {
                Bucket b = new Bucket();
                b.bucketList.add(3L);
                b.bucketList.add(4L);
                b.plan = "2017";
                b.paramList.put("UI_2017_01", "000000");
                b.paramList.put("UI_2017_02", "000001");
                ui.bucketList[1] = b;
            }

            conf.layers.put("UI", ui);
        }

        {
            Layer query = new Layer();

            query.bucketSize = 3L;
            query.factor = "QUERY";
            query.bucketList = new Bucket[2];
            {
                Bucket b = new Bucket();
                b.bucketList.add(0L);
                b.bucketList.add(1L);
                b.plan = "2010";
                b.paramList.put("QUERY_2010_01", "000000");
                b.paramList.put("QUERY_2010_02", "000001");
                query.bucketList[0] = b;
            }
            {
                Bucket b = new Bucket();
                b.bucketList.add(2L);
                b.plan = "2019";
                b.paramList.put("QUERY_2019_01", "000000");
                b.paramList.put("QUERY_2019_02", "000001");
                query.bucketList[1] = b;
            }

            conf.layers.put("QUERY", query);
        }

        {
            Layer ad = new Layer();

            ad.bucketSize = 2L;
            ad.factor = "AD";
            ad.bucketList = new Bucket[2];
            {
                Bucket b = new Bucket();
                b.bucketList.add(0L);
                b.plan = "2008";
                b.paramList.put("AD_2008_01", "000000");
                b.paramList.put("AD_2008_02", "000001");
                ad.bucketList[0] = b;
            }
            {
                Bucket b = new Bucket();
                b.bucketList.add(1L);
                b.plan = "2009";
                b.paramList.put("AD_2009_01", "000000");
                b.paramList.put("AD_2009_02", "000001");
                ad.bucketList[1] = b;
            }

            conf.layers.put("AD", ad);
        }

        return conf;
    }

}
