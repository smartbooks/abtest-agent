package com.suiyueyule.abtest.algorithm;

import java.util.Map;

/**
 * 抽象模型服务接口
 */
public interface BaseService {

    /**
     * 模型唯一标识
     *
     * @return
     */
    String getModelId();

    /**
     * 模型版本
     *
     * @return
     */
    String getModelVersion();

    /**
     * 模型适用的功能模块
     *
     * @return
     */
    String getModelTag();

    /**
     * 模型初始化
     */
    void init();

    /**
     * 模型预测
     *
     * @param param 输入参数集合
     * @return
     */
    Object predict(Map<String, Object> param);

    /**
     * 模型销毁
     */
    void destroy();

}
