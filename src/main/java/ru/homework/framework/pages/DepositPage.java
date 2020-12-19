package ru.homework.framework.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


import java.util.List;

public class DepositPage extends BasePage {
    private int resultReplenish;

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

    @Step("Выбор валюты {valute}")
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

    @Step("Заполнение текстового поля {fieldName} значением {value}")
    public DepositPage fillTextFields(String fieldName, String value) {
        for (WebElement element: textFields) {
            switch (fieldName){
                case "Сумма вклада":
                    String beforeFill = result.getText();
                    if (element.getAttribute("name").equals("amount")) {
                        fillField(element, value);
                        waitChangeText(result, beforeFill);
                    }
                    break;
                case "Ежемесячное пополнение":
                    if (element.getAttribute("name").equals("replenish")) {
                        beforeFill = result.getText();
                        fillField(element, value);
                        resultReplenish *= Integer.parseInt(value);
                        waitChangeText(result, beforeFill);
                    }
                    break;
                default:
                    Assert.fail("Поле " + fieldName + " не найдено на странице");
            }
        }
        return this;
    }

    @Step("Выбор срока депозита {depositTime}")
    public DepositPage setDepositTime(String depositTime) {
        Select select = new Select(selectElement);
        try {
            String beforeFill = result.getText();
            select.selectByVisibleText(depositTime);
            resultReplenish =Integer.parseInt(select.getAllSelectedOptions().get(0).getAttribute("value")) - 1;
            waitChangeText(result, beforeFill);
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

    @Step("Включение кнопки капитализация")
    public DepositPage capitalizationOn() {
        String beforeClick = result.getText();
        capitalizationButton.click();
        waitChangeText(result, beforeClick);
        WebElement currentCapitalization = capitalizationButton.findElement(By.xpath("./../span/div"));
        Assert.assertTrue("Капитализация не включилась",
                currentCapitalization.getAttribute("className").endsWith("checked"));
        return this;
    }

    @Step("Проверка начисленных процентов {earned}")
    public DepositPage checkEarnedPercent(String earned) {
        Assert.assertEquals("Сумма полученных процентов не совпадает",
                earned, earnedPercent.getText());
        return this;
    }

    @Step("Проверка ежемесячного пополнения")
    public DepositPage checkReplenish() {
        Assert.assertEquals("Сумма пополнения не совпадает",
                resultReplenish, Integer.parseInt(replenish.getText().replaceAll("\\D", "")));
        return this;
    }

    @Step("Проверка общей суммы {total}")
    public DepositPage checkResult(String total) {
        Assert.assertEquals("Итоговая сумма не совпадает",
                total, result.getText());
        return this;
    }

    private void waitChangeText(WebElement element, String oldText) {
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(element, oldText)));
    }
}
