package ru.appline.framework.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.*;


public class StartPage extends BasePage {
    private final static String NAME_PAGE = "StartPage";

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
    public SearchPage selectProductSearch(String nameProduct){
        elementToBeClickable(closeButton).click();
        fillInputField(searchElement, nameProduct);
        assertEquals("Наименование продукта " + nameProduct + " в графе поиск заполнено некорректно", nameProduct, searchElement.getAttribute("value"));
        searchElement.sendKeys(Keys.ENTER);
        return app.getSearchPage();
    }
}
