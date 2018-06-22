package com.suiyueyule.abtest.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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

}
