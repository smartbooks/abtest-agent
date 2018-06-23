package com.suiyueyule.abtest.core;

public class ABTestManager {

    private final static ABTestFactor instance = new ABTestFactor();

    public static ABTestFactor instance() {
        return instance;
    }
}
