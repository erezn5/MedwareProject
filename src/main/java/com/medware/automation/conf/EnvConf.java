package com.medware.automation.conf;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvConf {

    private static Properties props;

    static{
        initProps();
    }

    public static void initProps()  {
        props = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/main/java/resources/data.properties");
            props.load(fis);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static int getAsInteger(String property){
        return Integer.parseInt(props.getProperty(property));
    }

    public static String getProperty(String property){
        return props.getProperty(property);
    }
}
