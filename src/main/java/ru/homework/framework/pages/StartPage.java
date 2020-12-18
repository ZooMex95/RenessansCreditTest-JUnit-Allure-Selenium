package ru.homework.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class StartPage extends BasePage {
    @FindBy(xpath = "//div[@class='services services_main']/div/div[@class='service__title']/div")
    private List<WebElement> listOfTitles;


    public DepositPage getDepositPage(String title) {
        for (WebElement element: listOfTitles) {
            if (element.getText().equalsIgnoreCase(title)) {
                element.findElement(By.xpath("./../a")).click();
                return app.getDepositPage();
            }
        }
        Assert.fail("Поле " + title + " не найдено на стартовой странице");
        return null;
    }
}
