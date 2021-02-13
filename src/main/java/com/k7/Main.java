package com.k7;

import com.k7.configuration.AppProperties;
import com.k7.configuration.ConfigLoader;
import com.k7.configuration.SystemProperties;
import com.k7.services.SetEnvironmentService;

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




//        Properties properties = new Properties();
//        try {
//            if (System.getProperties().getProperty("contactbook.profile").equals("dev")) {
//                properties.load(new FileInputStream("app-dev.properties"));
//            } else properties.load(new FileInputStream("app-prod.properties"));
//            // properties.load(new FileInputStream("src/main/resources/app-prod.properties"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        SystemProperties systemProperties = new SystemProperties();
        ConfigLoader configLoader = new ConfigLoader();
        SystemProperties systemProperties = configLoader.getSystemProps(SystemProperties.class);
        String fileProperties = "app-"+systemProperties.getProfile()+".properties";
        AppProperties appProperties = configLoader.getFileProps(AppProperties.class,fileProperties);
        SetEnvironmentService setEnvironmentService = new SetEnvironmentService(appProperties);
        setEnvironmentService.start();
//        configLoader.getSystemProps(systemProperties);
//        System.out.println(systemProperties);
//        AppProperties appProperties = new AppProperties();
//        configLoader.getFileProps(appProperties,"app-dev.properties");
//        System.out.println(appProperties);




//System.out.println(System.getProperties().getProperty("contactbook.profile"));

//        Properties prop = System.getProperties();
//        for (Map.Entry<Object, Object> proper : prop.entrySet()) {
//            System.out.println(proper.getKey() + ":" + proper.getValue());


//        Class test = ApiContactService.class;
//        List<Field> method = Arrays.asList(test.getDeclaredFields());
//        for (Field method1 : method) {
//            System.out.println(method1.getName()+" "+method1.getType());
//            }
//            System.out.println("-------------------------------");
        }
    }

