package com.lyl.jvm.simple;

import java.io.IOException;
import java.util.Properties;

/**
 * description Singleton3
 *
 * @author liuyanling
 * @date 2021-10-12 15:08
 */
public class Singleton3 {

    public static final Singleton3 INSTANCE;
    private String info;

    static {
//        INSTANCE = new Singleton3();

        Properties properties = new Properties();
        try {
            properties.load(Singleton3.class.getClassLoader().getResourceAsStream("single.properties"));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        INSTANCE = new Singleton3(properties.getProperty("info"));
    }

    private Singleton3() {
    }

    private Singleton3(String info) {
        this.info = info;

    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Singleton3{" +
                "info='" + info + '\'' +
                '}';
    }
}
