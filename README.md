
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
req:curl http://localhost:8080/abtest-agent/ab/query?k=1&v=1,2,3&k=3,6,9&p=open
rep:{"data":{"p":["open"],"v":["1,2,3"],"k":["1","3,6,9"]}}
```

## Referer
- http://uxren.cn/?p=58841
- https://exp-platform.com/
- Google:https://ai.google/research/pubs/pub36500
- CSDN:https://www.csdn.net/article/2015-01-09/2823499
- 技术揭秘大众点评大规模并行AB测试框架Gemini:https://www.csdn.net/article/2015-03-24/2824303