package ru.homework.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.homework.framework.managers.ManagerPages;


import static ru.homework.framework.managers.DriverManager.getDriver;

public class BasePage {
    protected ManagerPages app = ManagerPages.getManagerPages();

    protected WebDriverWait wait = new WebDriverWait(getDriver(), 20, 1000);

    protected JavascriptExecutor js = (JavascriptExecutor) getDriver();

    public BasePage() {
        PageFactory.initElements(getDriver(), this);
    }

    protected void scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true)", element);
    }

    protected void fillField(WebElement element, String value) {
        element.click();
        element.sendKeys(value);
        Assert.assertEquals("Поле заполнено не верно",
                element.getAttribute("value").replaceAll("\\D", ""), value);

    }
}
