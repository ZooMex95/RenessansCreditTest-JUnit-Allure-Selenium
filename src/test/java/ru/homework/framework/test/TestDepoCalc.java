package ru.homework.framework.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.homework.framework.basetest.BaseTest;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestDepoCalc extends BaseTest {
    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                {"Рубли", "300000", "6 месяцев", "50000", "9 132,17", "559 132,17"},
                {"Доллары США", "50000", "12 месяцев", "3000", "100,36", "83 100,36"},
        });
    }

    @Parameterized.Parameter
    public String valute;
    @Parameterized.Parameter(1)
    public String firstPay;
    @Parameterized.Parameter(2)
    public String depositTime;
    @Parameterized.Parameter(3)
    public String monthlyPay;
    @Parameterized.Parameter(4)
    public String percent;
    @Parameterized.Parameter(5)
    public String result;


    @Test
    public void test() {
        app.getStartPage()
        .getDepositPage("Вклады")
        .selectValute(valute)
        .fillTextFields("Сумма вклада", firstPay)
        .setDepositTime(depositTime)
        .fillTextFields("Ежемесячное пополнение", monthlyPay)
        .capitalizationOn()
        .checkEarnedPercent(percent)
        .checkReplenish()
        .checkResult(result);
    }
}
