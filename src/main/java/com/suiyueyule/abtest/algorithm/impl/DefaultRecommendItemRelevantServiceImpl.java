package com.suiyueyule.abtest.algorithm.impl;

import com.suiyueyule.abtest.algorithm.BaseService;
import com.suiyueyule.abtest.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * 默认推荐用户喜欢的物品的相关物品列表
 */
public class DefaultRecommendItemRelevantServiceImpl implements BaseService {

    private final static Logger logger = LogManager.getLogger(DefaultRecommendItemRelevantServiceImpl.class);

    @Override
    public String getModelId() {
        //明文2
        return "C81E728D9D4C2F636F067F89CC14862C";
    }

    @Override
    public String getModelVersion() {
        return "0.1";
    }

    @Override
    public String getModelTag() {
        return "RECOMMEND_ITEM_RELEVANT_XGTJ_DEFAULT";
    }

    @Override
    public void init() {

    }

    @Override
    public Object predict(Map<String, Object> param) {

        logger.debug(String.format("推测测试,请求参数:%s", JsonUtils.toJson(param, false)));

        return new Long[]{100L, 200L, 300L, 400L, 500L};
    }

    @Override
    public void destroy() {

    }
}
