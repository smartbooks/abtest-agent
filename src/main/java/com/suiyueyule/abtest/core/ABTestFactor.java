package com.suiyueyule.abtest.core;

import java.util.HashMap;
import java.util.Map;

public class ABTestFactor {

    private ExperimentConfiguration conf = new ExperimentConfiguration();
    private final static byte[] lock = new byte[0];

    public void refreshConfiguration(ExperimentConfiguration configuration) {
        synchronized (lock) {
            conf = configuration;
        }
    }

    public ABTestResult run(Long cookie, String layerKey) {

        if (conf.layers.containsKey(layerKey) == false) {
            return null;
        }

        Layer layer = conf.layers.get(layerKey);

        if (layer.bucketList == null || layer.bucketList.length == 0) {
            return null;
        }

        long bucketNum = MultiLayerExperiment.getBucketNum(cookie, layer.bucketSize, layer.factor);

        Bucket bucket = null;
        for (int i = 0; i < layer.bucketList.length; i++) {
            if (layer.bucketList[i].bucketList.contains(bucketNum)) {
                bucket = layer.bucketList[i];
                break;
            }
        }

        if (null == bucket) {
            return null;
        }

        ABTestResult abTestResult = new ABTestResult();
        abTestResult.bucket = bucketNum;
        abTestResult.cookie = cookie;
        abTestResult.layer = layerKey;
        abTestResult.plan = bucket.plan;
        abTestResult.param = bucket.paramList;

        return abTestResult;
    }

    public class ABTestResult {

        private Map<String, Object> param = new HashMap<>();
        private Long cookie;
        private String plan;
        private String layer;
        private Long bucket;

        public Map<String, Object> getParam() {
            return param;
        }

        public void setParam(Map<String, Object> param) {
            this.param = param;
        }

        public Long getCookie() {
            return cookie;
        }

        public void setCookie(Long cookie) {
            this.cookie = cookie;
        }

        public String getPlan() {
            return plan;
        }

        public void setPlan(String plan) {
            this.plan = plan;
        }

        public String getLayer() {
            return layer;
        }

        public void setLayer(String layer) {
            this.layer = layer;
        }

        public Long getBucket() {
            return bucket;
        }

        public void setBucket(Long bucket) {
            this.bucket = bucket;
        }
    }

}
