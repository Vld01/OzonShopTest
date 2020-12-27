package ru.appline.framework.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class StartPage extends BasePage {
    @FindBy(xpath = "//div/div[text()='Вклады']/../a[@href='/contributions/']")
    WebElement depositElement;

    /**
     * Функция выбора из подменю вклады
     *
     * @return DepositPage - т.е. переходим на страницу {@link DepositPage}
     */
    @Step("Выбираем подменю вклады")
    public DepositPage choiceDeposit(){
        elementToBeClickable(depositElement).click();
        return app.getDepositPage();
    }
}
