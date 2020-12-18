package ru.homework.framework.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestPropMeneger {
    private final Properties properties = new Properties();

    private static TestPropMeneger INSTANCE = null;

    private TestPropMeneger() {
        try {
            properties.load(new FileInputStream(
                    new File("src/main/resources/" +
                            System.getProperty("env","application") + ".properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TestPropMeneger getTestPropManager() {
        if (INSTANCE == null) {
            INSTANCE = new TestPropMeneger();
        }
        return INSTANCE;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}