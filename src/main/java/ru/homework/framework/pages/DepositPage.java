package ru.homework.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;


import java.util.List;

public class DepositPage extends BasePage {
    @FindBy(xpath = "//div[@class='calculator__currency-row']/div/label/span/span")
    private List<WebElement> valutes;

    @FindBy(xpath = "//input[@type='text']")
    private List<WebElement> textFields;

    @FindBy(xpath = "//span[text()='Ежемесячная капитализация']/..")
    private WebElement capitalizationButton;

    @FindBy(tagName = "SELECT")
    private WebElement selectElement;

    @FindBy(xpath = "//span[@class='js-calc-earned']")
    private WebElement earnedPercent;

    @FindBy(xpath = "//span[@class='js-calc-replenish']")
    private WebElement replenish;

    @FindBy(xpath = "//span[@class='js-calc-result']")
    private WebElement result;

    public DepositPage selectValute(String valute) {
        for (WebElement element: valutes) {
            if (element.getText().equalsIgnoreCase(valute)){
                element.findElement(By.xpath("./..")).click();
                return this;
            }
        }
        Assert.fail("Поле " + valute + " не найдено на странице калькулятора");
        return this;
    }

    public DepositPage fillTextFields(String fieldName, String value) {
        for (WebElement element: textFields) {
            switch (fieldName){
                case "Сумма вклада":
                    if (element.getAttribute("name").equals("amount")) {
                        fillField(element, value);
                    }
                    break;
                case "Ежемесячное пополнение":
                    if (element.getAttribute("name").equals("replenish")) {
                        fillField(element, value);
                    }
                    break;
                default:
                    Assert.fail("Поле " + fieldName + " не найдено на странице");
            }
        }
        return this;
    }

    public DepositPage setDepositTime(String depositTime) {
        Select select = new Select(selectElement);
        try {
            select.selectByVisibleText(depositTime);
            Assert.assertEquals("Срок вклада выбран не верно",
                    depositTime, select.getAllSelectedOptions().get(0).getText());
            return this;
        } catch (NoSuchElementException e) {
            System.out.println("Не найдено поле " + depositTime);
            System.out.println("Верные варианты: ");
            select.getOptions().forEach(l-> System.out.println(" " + l.getText()));
            return this;
        }
    }

    public DepositPage capitalizationOn() {
        //scrollToElementJs(capitalizationButton);
        capitalizationButton.click();
        WebElement currentCapitalization = capitalizationButton.findElement(By.xpath("./../span/div"));
        Assert.assertTrue("Капитализация не включилась",
                currentCapitalization.getAttribute("className").endsWith("checked"));
        return this;
    }

    public DepositPage checkEarnedPercent(String earned) {
        Assert.assertEquals("Сумма полученных процентов не совпадает",
                earned, earnedPercent.getText());
        return this;
    }

    public DepositPage checkReplenish(String earned) {
        Assert.assertEquals("Сумма пополнения не совпадает",
                earned, replenish.getText());
        return this;
    }

    public DepositPage checkResult(String earned) {
        Assert.assertEquals("Итоговая сумма не совпадает",
                earned, result.getText());
        return this;
    }


}
