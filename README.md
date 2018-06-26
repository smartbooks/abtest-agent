
# attest-agent

## build
```
git clone https://github.com/smartbooks/abtest-agent
cd ./abtest-agent
export RECOMMEND_CONF_DIR=`pwd`

mvn clean package jetty:run
mvn org.mortbay.jetty:maven-jetty-plugin:run

curl http://localhost:8080/abtest-agent/
curl http://localhost:8080/abtest-agent/ab/hi


curl localhost:8080/ab/item?reg_uid=103

{
    "msg": "success",
    "code": "100",
    "data": {
        "ab_plan": "201806B",
        "ab_layer": "RECOMMEND_ITEM",
        "ab_bucket": 1,
        "ab_model_ver": "0.1",
        "item_list": [],
        "ab_model_id": "F899139DF5E1059396431415E770C6DD",
        "ab_model_tag": "RECOMMEND_ITEM_CNXH_CF"
    },
    "time": 1529996151308,
    "status": 0
}

curl localhost:8080/ab/itemRelevant?reg_uid=21&item_id=100

{
    "msg": "success",
    "code": "100",
    "data": {
        "ab_plan": "201806B",
        "ab_layer": "RECOMMEND_ITEM_RELEVANT",
        "ab_bucket": 1,
        "ab_model_ver": "0.1",
        "item_list": [],
        "ab_model_id": "38B3EFF8BAF56627478EC76A704E9B52",
        "ab_model_tag": "RECOMMEND_ITEM_RELEVANT_XGTJ_CF"
    },
    "time": 1529996129584,
    "status": 0
}

```

## Referer
- http://uxren.cn/?p=58841
- https://exp-platform.com/
- Google:https://ai.google/research/pubs/pub36500
- CSDN:https://www.csdn.net/article/2015-01-09/2823499
- 技术揭秘大众点评大规模并行AB测试框架Gemini:https://www.csdn.net/article/2015-03-24/2824303