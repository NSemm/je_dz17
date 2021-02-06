package com.k7;

import com.k7.utility.SetEnvironmentService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
//        Properties properties = new Properties();
//        try {
//            if (System.getProperties().getProperty("contactbook.profile").equals("dev")) {
//                properties.load(Main.class.getClassLoader().getResourceAsStream("app-dev.properties"));
//            } else properties.load(Main.class.getClassLoader().getResourceAsStream("app-prod.properties"));
//        } catch (
//                IOException e) {
//            e.printStackTrace();
//        }
        Properties properties = new Properties();
        try {
            if (System.getProperties().getProperty("contactbook.profile").equals("dev")) {
                properties.load(new FileInputStream("src/main/resources/app-dev.properties"));
            } else properties.load(new FileInputStream("src/main/resources/app-prod.properties"));
            // properties.load(new FileInputStream("src/main/resources/app-prod.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SetEnvironmentService setEnvironmentService = new SetEnvironmentService(properties);
        setEnvironmentService.start();
//System.out.println(System.getProperties().getProperty("contactbook.profile"));

//        Properties prop = System.getProperties();
//        for (Map.Entry<Object, Object> proper : prop.entrySet()) {
//            System.out.println(proper.getKey() + ":" + proper.getValue());
    }
}

