package com.suiyueyule.abtest.algorithm.impl;

import com.suiyueyule.abtest.action.RecommendItemRelevantAction;
import com.suiyueyule.abtest.algorithm.BaseService;
import com.suiyueyule.abtest.util.JedisUtils;
import com.suiyueyule.abtest.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * 推荐用户喜欢的物品的相关物品列表-协同过滤版本
 */
public class CFRecommendItemRelevantServiceImpl implements BaseService {

    private final static Logger logger = LogManager.getLogger(CFRecommendItemRelevantServiceImpl.class);
    private final static Double displayWeight = 0.01;

    @Override
    public String getModelId() {
        //明文100
        return "38B3EFF8BAF56627478EC76A704E9B52";
    }

    @Override
    public String getModelVersion() {
        return "0.1";
    }

    @Override
    public String getModelTag() {
        return "RECOMMEND_ITEM_RELEVANT_XGTJ_CF";
    }

    @Override
    public void init() {

    }

    @Override
    public Object predict(Map<String, Object> param) {

        logger.debug(String.format("请求参数:%s", JsonUtils.toJson(param, false)));

        Jedis jedis = null;

        try {

            long regUid = (Long) param.get(RecommendItemRelevantAction.regUidKey);
            long itemUid = (Long) param.get(RecommendItemRelevantAction.itemIdKey);
            int pageSize = (Integer) param.get(RecommendItemRelevantAction.pageSizeKey);

            //modelTag:modelId:modelVer:uid:itemId
            String redisKey = String.format("%s:%s:%s:%s:%s", getModelTag(), getModelId(), getModelVersion(), regUid, itemUid);

            jedis = JedisUtils.getJedis();

            //redis zrange默认返回顺序从小到大,推荐物品偏好入库redis时已将评分倒置存储,即从大到小由5->1转1-5
            Iterator<String> it = jedis.zrange(redisKey, 0, pageSize).iterator();

            ArrayList<Long> rs = new ArrayList<>();
            while (it.hasNext()) {
                String cur = it.next();

                //已推荐的结果展示权重要相应递减,在redis中表现为评分增加(由小到大排列)
                jedis.zincrby(redisKey, displayWeight, cur);

                rs.add(Long.valueOf(cur));
            }

            return rs.toArray();

        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }

        return new Long[0];
    }

    @Override
    public void destroy() {

    }
}
