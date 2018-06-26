package com.suiyueyule.abtest.algorithm.impl;

import com.suiyueyule.abtest.action.RecommendItemAction;
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
 * 推荐用户喜欢的物品列表-协同过滤版本
 */
public class CFRecommendItemServiceImpl implements BaseService {

    private final static Logger logger = LogManager.getLogger(CFRecommendItemServiceImpl.class);
    private final static Double displayWeight = 0.01;

    @Override
    public String getModelId() {
        //明文100
        return "F899139DF5E1059396431415E770C6DD";
    }

    @Override
    public String getModelVersion() {
        return "0.1";
    }

    @Override
    public String getModelTag() {
        return "RECOMMEND_ITEM_CNXH_CF";
    }

    @Override
    public void init() {

    }

    @Override
    public Object predict(Map<String, Object> param) {

        logger.debug(String.format("请求参数:%s", JsonUtils.toJson(param, false)));

        Jedis jedis = null;

        try {

            long regUid = (Long) param.get(RecommendItemAction.regUidKey);
            int pageSize = (Integer) param.get(RecommendItemAction.pageSizeKey);

            //modelTag:modelId:modelVer:uid
            //RECOMMEND_ITEM_CNXH_CF:F899139DF5E1059396431415E770C6DD:0.1:103
            String redisKey = String.format("%s:%s:%s:%s", getModelTag(), getModelId(), getModelVersion(), regUid);

            jedis = JedisUtils.getJedis();

            logger.debug(String.format("获取缓存:%s", redisKey));

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
