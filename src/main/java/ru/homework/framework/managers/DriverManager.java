package ru.homework.framework.managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static ru.homework.framework.utils.PropConst.*;


public class DriverManager {

    private static WebDriver driver;

    private static TestPropMeneger props = TestPropMeneger.getTestPropManager();

    private DriverManager() {

    }

    private static void initDriver() {
        if (props.getProperty(TYPE_BROWSER).equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", props.getProperty(PATH_CHROME_DRIVER));
            driver = new ChromeDriver();
        }
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    public static void quitDriver() {
        driver.quit();
        driver = null;
    }
}
