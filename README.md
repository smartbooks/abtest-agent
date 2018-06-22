
# attest-agent

## build
```
mvn clean package
mvn org.mortbay.jetty:maven-jetty-plugin:run
mvn jetty:run

curl http://localhost:8080/abtest-agent/
curl http://localhost:8080/abtest-agent/ab/hi
req:curl http://localhost:8080/abtest-agent/ab/query?k=1&v=1,2,3&k=3,6,9&p=open
rep:{"data":{"p":["open"],"v":["1,2,3"],"k":["1","3,6,9"]}}
```