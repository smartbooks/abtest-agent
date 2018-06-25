package com.suiyueyule.abtest;

import com.suiyueyule.abtest.action.AbForwardAction;
import com.suiyueyule.abtest.core.ExperimentConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class App {

    public static void main(String[] args) {
        System.out.println("Hello World");
        
        yamlExample();
    }

    public static void yamlExample() {
        Yaml yaml = new Yaml();

        String yamlConfig = yaml.dump(new AbForwardAction().genericExperimentConfiguration());
        System.out.println(yamlConfig);

        try {
            ExperimentConfiguration conf = yaml.load(new FileInputStream("/Users/wangya/Documents/test.yaml"));

            conf.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
