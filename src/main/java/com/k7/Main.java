package com.k7;

import com.k7.configuration.AppProperties;
import com.k7.configuration.ConfigLoader;
import com.k7.configuration.SystemProperties;
import com.k7.services.SetEnvironmentService;

public class Main {
    public static void main(String[] args) {
        ConfigLoader configLoader = new ConfigLoader();
        SystemProperties systemProperties = configLoader.getSystemProps(SystemProperties.class);
        String fileProperties = "app-" + systemProperties.getProfile() + ".properties";
        AppProperties appProperties = configLoader.getFileProps(AppProperties.class, fileProperties);
        SetEnvironmentService setEnvironmentService = new SetEnvironmentService(appProperties);
        setEnvironmentService.start();

    }
}

