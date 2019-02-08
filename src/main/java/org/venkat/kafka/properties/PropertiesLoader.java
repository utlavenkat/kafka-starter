package org.venkat.kafka.properties;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private static Properties configProperties = null;
    static {
        try {
             configProperties = new Properties();
            InputStream inputStream = PropertiesLoader.class.getClassLoader().getResourceAsStream("application.properties");
            configProperties.load(inputStream);
        }
        catch(Exception e){
            System.out.println("Could not load the file");
            e.printStackTrace();
        }
    }

    public static String getPropertyValue(String propertyName) {
        return configProperties.getProperty(propertyName);
    }
}
