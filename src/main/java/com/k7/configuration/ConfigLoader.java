package com.k7.configuration;

import com.k7.annotation.ReadProp;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class ConfigLoader {

    public<T> T getSystemProps(Class<T> clazz) {
        Object object = createObject(clazz);
       extractedProperties(object,System.getProperties());
       return (T)object;
    }

    public<T> T getFileProps(Class<T> clazz, String file) {
        try (InputStream readFile = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(readFile);
            Object object = createObject(clazz);
            extractedProperties(object, properties);
            return (T)object;
        } catch (IOException e) {
           throw new RuntimeException("Fail load properties from "+ file,e);
        }

    }
    private Object createObject(Class clazz){
        try {
            Constructor constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException|IllegalAccessException|InstantiationException|InvocationTargetException e) {
            throw new RuntimeException("Constructor must be declared",e);
        }

    }

    private void extractedProperties(Object object, Properties properties) {
        Class clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ReadProp.class)) {
                ReadProp annotation = field.getAnnotation(ReadProp.class);
                String propValue = properties.getProperty(annotation.value());
                field.setAccessible(true);
                try {
                    field.set(object, propValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
