package com.suiyueyule.abtest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class JsonUtils {

    private final static Logger logger = LogManager.getLogger(JsonUtils.class);

    /**
     * 对象转JSon
     *
     * @param obj
     * @param pretty
     * @return
     */
    public static String toJson(Object obj, boolean pretty) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (pretty) {
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            } else {
                return mapper.writeValueAsString(obj);
            }
        } catch (JsonProcessingException e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * json转对象
     *
     * @param json
     * @param valueType
     * @return
     */
    public static Object toObject(String json, Class<?> valueType) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, valueType);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

}
