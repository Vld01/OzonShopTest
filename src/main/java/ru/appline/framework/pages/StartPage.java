package ru.appline.framework.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.*;


public class StartPage extends BasePage {
    @FindBy(xpath = "//input[@* = 'Искать на Ozon']")
    WebElement searchElement;

    @FindBy(xpath = "//div[text() = 'Везите скорее']/../../../../..//button[@type = 'button']/div")
    WebElement closeButton;

    /**
     * Функция поиска продукта
     *
     * @param nameProduct - наименование продукта
     * @return SearchPage - т.е. переходим на страницу {@link ru.appline.framework.pages.SearchPage}
     */
    @Step("Поиск продукта '{nameProduct}'")
    public SearchPage selectProductSearch(String nameProduct){
        elementToBeClickable(closeButton).click();
        fillInputField(searchElement, nameProduct);
        assertEquals("Наименование продукта " + nameProduct + " в графе поиск заполнено некорректно", nameProduct, searchElement.getAttribute("value"));
        searchElement.sendKeys(Keys.ENTER);
        return app.getSearchPage();
    }
}
