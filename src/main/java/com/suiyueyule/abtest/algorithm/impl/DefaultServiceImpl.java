package com.suiyueyule.abtest.algorithm.impl;

import com.suiyueyule.abtest.algorithm.BaseService;
import com.suiyueyule.abtest.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class DefaultServiceImpl implements BaseService {

    private final static Logger logger = LogManager.getLogger(DefaultServiceImpl.class);

    @Override
    public String getModelId() {
        return "cfcd208495d565ef66e7dff9f98764da";
    }

    @Override
    public String getModelVersion() {
        return "0.1";
    }

    @Override
    public String getModelTag() {
        return "cainixihuan";
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
