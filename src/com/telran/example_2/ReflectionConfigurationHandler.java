package com.telran.example_2;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ReflectionConfigurationHandler {

    private static final String APPLICATION_PROPERTIES_NAME = "/application.properties";


    //read properties
    //read fields
    //set fields

    //3    ApplicationConfiguration       Application.Configuration.class
    public static <T> T getConfiguration(Class<T> configurationClass) {
        try {
            Properties properties = readProperties();
            List<Field> fields = getFields(configurationClass);

            T instance = configurationClass.newInstance();

            fields.forEach(x -> {
                x.setAccessible(true);
                try {

                    x.set(instance, properties.getProperty(x.getName(), null));
                } catch (IllegalAccessException e) { //if field is final
                    throw new RuntimeException("Field misconfiguration (possibly 'final': ", e);
                }
            });

            return instance;

        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("ApplicationConfiguration misconfiguration: ", e);
        }
    }

    //2
    private static List<Field> getFields(Class clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
    }

    //1
    private static Properties readProperties() {
        try {
            Properties properties = new Properties();
            InputStream inputStream = Main.class.getResourceAsStream(APPLICATION_PROPERTIES_NAME);
            properties.load(inputStream);

            return properties;

        } catch (Exception e) {
            throw new RuntimeException("Everything is broken", e);
        }
    }
}
