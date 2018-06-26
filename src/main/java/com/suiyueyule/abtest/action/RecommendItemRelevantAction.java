package com.suiyueyule.abtest.action;


import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.suiyueyule.abtest.algorithm.BaseService;
import com.suiyueyule.abtest.algorithm.impl.DefaultRecommendItemRelevantServiceImpl;
import com.suiyueyule.abtest.core.ABTestFactor;
import com.suiyueyule.abtest.core.ABTestManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 推荐用户喜欢的物品的相关物品列表
 */
public class RecommendItemRelevantAction extends ActionSupport {

    private final static Logger logger = LogManager.getLogger(RecommendItemRelevantAction.class);

    /**
     * ab分流实验所在的层
     */
    private final static String layoutName = "RECOMMEND_ITEM_RELEVANT";

    /**
     * 请求参数推荐用户键名
     */
    private final static String regUidKey = "reg_uid";

    /**
     * 请求参数相似物品ID
     */
    private final static String itemIdKey = "item_id";

    /**
     * 请求参数返回条数键名
     */
    private final static String pageSizeKey = "page_size";

    /**
     * 默认返回条数
     */
    private final static Integer defaultPageSize = 5;

    /**
     * 返回结果
     */
    private Map<String, Object> jsonData;

    public Map<String, Object> getJsonData() {
        return jsonData;
    }

    public void setJsonData(Map<String, Object> jsonData) {
        this.jsonData = jsonData;
    }

    public String itemRelevant() {

        jsonData = new HashMap<>();

        try {

            //解析参数
            Map<String, Object> param = parseRequestParam();

            //验证参数
            if (validateRequestParam(param)) {

                //ab测试分流洗牌
                ABTestFactor.ABTestResult layerShuffle = ABTestManager.instance().run((Long) param.get(regUidKey), layoutName);

                //实例化模型服务接口
                BaseService modelService;

                //没有匹配的分流策略
                if (null == layerShuffle) {
                    //与请求分数map合并
                    param.putAll(layerShuffle.getParam());
                    modelService = new DefaultRecommendItemRelevantServiceImpl();
                } else {
                    modelService = (BaseService) Class.forName(layerShuffle.getClassTag()).newInstance();
                }

                //向用户推荐物品列表
                Object itemData = modelService.predict(param);

                //返回结果正文构建
                Map<String, Object> resultMap = new HashMap<>();

                //设置ab测试穿透的层和落入的桶，以及模型名称和版本信息

                //没有匹配的分流策略
                if (null != layerShuffle) {
                    resultMap.put("ab_layer", layerShuffle.getLayer());
                    resultMap.put("ab_bucket", layerShuffle.getBucket());
                    resultMap.put("ab_plan", layerShuffle.getPlan());
                }

                resultMap.put("ab_model_id", modelService.getModelId());
                resultMap.put("ab_model_ver", modelService.getModelVersion());
                resultMap.put("ab_model_tag", modelService.getModelTag());
                resultMap.put("item_list", itemData);

                //设置成功返回结果
                jsonData.put("data", resultMap);
                jsonData.put("status", 0);
                jsonData.put("code", "100");
                jsonData.put("msg", "success");

            } else {
                //设置参数校验失败返回结果
                jsonData.put("data", null);
                jsonData.put("status", 1);
                jsonData.put("code", "101");
                jsonData.put("msg", "param validata failure.");
            }

            jsonData.put("time", System.currentTimeMillis());

        } catch (Exception e) {

            logger.error(e);

            //设置未知异常返回结果
            jsonData.put("data", null);
            jsonData.put("status", 1);
            jsonData.put("code", "102");
            jsonData.put("msg", "unknown error.");
        }

        return Action.SUCCESS;
    }

    /**
     * 验证请求参数
     *
     * @param param
     * @return
     */
    private Boolean validateRequestParam(Map<String, Object> param) {

        if (param.containsKey(regUidKey) == false || (Long) param.get(regUidKey) <= 0) {
            return false;
        }

        if (param.containsKey(itemIdKey) == false || (Long) param.get(itemIdKey) <= 0) {
            return false;
        }

        return true;
    }

    /**
     * 解析请求参数
     *
     * @return
     */
    private Map<String, Object> parseRequestParam() {

        HttpServletRequest request = ServletActionContext.getRequest();

        String pamRegUid = request.getParameter(regUidKey);
        String pamPageSize = request.getParameter(pageSizeKey);
        String pamItemId = request.getParameter(itemIdKey);

        Map<String, Object> param = new HashMap<>();

        //推荐用户UID
        if (null != pamRegUid && pamRegUid.isEmpty() == false) {
            param.put(regUidKey, Long.valueOf(pamRegUid.trim()));
        }

        //相似物品ID
        if (null != pamItemId && pamItemId.isEmpty() == false) {
            param.put(itemIdKey, Long.valueOf(pamItemId.trim()));
        }

        //返回条数
        if (null != pamPageSize && pamPageSize.length() > 0) {
            param.put(pageSizeKey, Integer.valueOf(pamPageSize.trim()));
        } else {
            param.put(pageSizeKey, defaultPageSize);
        }

        return param;
    }

}
