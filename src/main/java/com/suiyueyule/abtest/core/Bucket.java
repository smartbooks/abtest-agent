package com.suiyueyule.abtest.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Bucket {

    public Map<String, Object> paramList = new HashMap<>();

    public HashSet<Long> bucketList = new HashSet<>();

    public String plan;

    public String classTag;
}
