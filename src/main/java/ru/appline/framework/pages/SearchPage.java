package ru.appline.framework.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.appline.framework.utils.Product;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static ru.appline.framework.managers.DriverManager.getDriver;
import static ru.appline.framework.managers.InitManager.props;
import static ru.appline.framework.utils.PropConst.IMPLICITLY_WAIT;


public class SearchPage extends BasePage {
    @FindBy(xpath = "//div[contains (text(), 'Цена')]/..//p[contains (text(), 'до')]/../input")
    WebElement searchUpLimitElement;

    @FindBy(xpath = "//*[text() = 'NFC']/../..//input")
    WebElement nfcCheckBox;

    @FindBy(xpath = "//*[text() = 'Xiaomi']/../..//input")
    WebElement xiaomiCheckBox;

    @FindBy(xpath = "//*[text() = 'Samsung']/../..//input")
    WebElement samsungCheckBox;

    @FindBy(xpath = "//*[text() = 'Beats']/../..//input")
    WebElement beatsCheckBox;

    @FindBy(xpath = "//*[contains(text(), 'Высокий рейтинг')]/../../..//input")
    WebElement maxRatingCheckBox;

    @FindBy(xpath = "//div[@* = 'searchResultsV2']/div/div")
    @CacheLookup
    List<WebElement> allSearchElements;

    @FindBy(xpath = "//div[contains(text(), 'Бренды')]/..//span[contains(text(), 'Посмотреть все')]")
    WebElement viewAllBrandsElements;

    @FindBy(xpath = "//div[contains(text(), 'Бренды')]/..//span[contains(text(), 'Свернуть')]")
    WebElement hideAllBrandsElements;

    @FindBy(xpath = "//header/div/div/a/span[text() = 'Корзина']/../span[position() = 1]")
    WebElement basketCountElement;

    @FindBy(xpath = "//header/div/div/a/span[text() = 'Корзина']/..//*[@class][position() = 2]")
    WebElement basketElement;

    @FindBy(xpath = "//div[contains(@data-widget, 'searchResultsFilters')]")
    WebElement searchFiltersElement;


    /**
     * Функция ограничения товара по верхней границе
     *
     * @param upLimit - верхняя граница цены
     * @return SearchPage - т.е. остаемся на этой странице
     */
    @Step("Ограничение цены до '{upLimit}")
    public SearchPage clickProductSearch(Integer upLimit){
        scrollToElementJs(searchUpLimitElement);
        while (!searchUpLimitElement.getAttribute("value").isEmpty()) {
            searchUpLimitElement.sendKeys(Keys.BACK_SPACE);
        }
        fillInputField(searchUpLimitElement, upLimit);
        explicitWait(500);
        action.sendKeys(Keys.ENTER);
        utilWaitSearchFiltersElement("" + upLimit);
        assertEquals("Ограничение цены " + upLimit + " в графе 'до' заполнено некорректно", "" + upLimit, searchUpLimitElement.getAttribute("value"));
        return app.getSearchPage();
    }

    /**
     * Метод заполнения чекбоксов
     *
     * @param nameField - имя веб элемента, поля ввода
     * @return SearchPage - т.е. остаемся на этой странице
     */
    @Step("Заполняем поле '{nameField}' значением '{value}'")
    public SearchPage fillCheckbox(String nameField, String value) {
        WebElement element = null;
        switch (nameField) {
            case "Высокий рейтинг":
                element = maxRatingCheckBox;
                break;
            case "NFC":
                element = nfcCheckBox;
                break;
            case "Beats":
                viewAllBrandsElements();
                element = beatsCheckBox;
                break;
            case "Samsung":
                viewAllBrandsElements();
                element = samsungCheckBox;
                break;
            case "Xiaomi":
                viewAllBrandsElements();
                element = xiaomiCheckBox;
                break;
            default:
                fail("Поле с наименованием '" + nameField + "' отсутствует на странице " +
                        "'Поиска'");

        }
        scrollToElementJs(element.findElement(By.xpath("./..")));
        if(Boolean.parseBoolean(value) ^ Boolean.parseBoolean(element.getAttribute("checked"))){
            element.findElement(By.xpath("./..")).click();
            try{
                wait.until(ExpectedConditions.attributeContains(element, "checked", value));
                utilWaitSearchFiltersElement(nameField); //ожидалка в цикле своя отмечен или не отмечен
            } catch (TimeoutException e){
                fail("Элемент '"+ nameField +"' не был установлен в необходимое значение " + value);
            }
        }
        return this;
    }

    /**
     * Метод добавления четных элементов в корзину
     *
     * @param countProduct - количество добавляемых продуктов
     * @return BasketPage -  т.е. переходим на страницу {@link ru.appline.framework.pages.BasketPage}
     */
    @Step("Добавляем в корзину первые '{countProduct}' четных товара(ов)")
    public BasketPage fillCheckbox(Integer countProduct) {
        String nameProduct = "";
        int priceProduct = 0;
        for(int i = 0, j = 0; j < countProduct; i++) {
            WebElement elm = allSearchElements.get(i * 2 + 1);
            if (!isElementCorrected(elm,"Express") && isElementCorrected(elm,"В корзину")) {
                scrollToElementJs(elm.findElement(By.xpath("./div/div/div/div/a")));
                elementToBeClickable(elm.findElement(By.xpath(".//div[text() = 'В корзину']/.."))).click();
                nameProduct = elm.findElement(By.xpath("./div/div/div/div/a")).getText();
                priceProduct = utilParsInteger(elm.findElement(By.xpath(".//a/div/span[1]")).getText());
                listProducts.add(new Product(nameProduct, priceProduct));
                j++;
                wait.until(ExpectedConditions.textToBePresentInElement(basketCountElement, "" + j));
            }
        }
        elementToBeClickable(basketElement).click();
        return app.getBasketPage();
    }

    /**
     * Метод открытия всплывающего окна брендов
     */
    public void viewAllBrandsElements(){
        try {
            getDriver().manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
            scrollToElementJs(viewAllBrandsElements);
            viewAllBrandsElements.click();
            elementToBeVisible(hideAllBrandsElements);
        } catch (NoSuchElementException | StaleElementReferenceException e) {
        } finally {
            getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(props.getProperty(IMPLICITLY_WAIT)), TimeUnit.SECONDS);
        }
    }

    /**
     * Метод ожидания наименования фильтра поиска сверху над товарами
     */
    public void utilWaitSearchFiltersElement (String s){
        int i = 0;
        scrollToElementJs(searchFiltersElement);
        boolean isPresentText = false;
        while (!isPresentText && i < 20) {
            try {
                isPresentText = searchFiltersElement.getText().replaceAll("[^A-Za-zА-Яа-я0-9]", "").contains(s);
                explicitWait(500);
                i++;
            } catch (StaleElementReferenceException e) {
                System.out.println("StaleElementReferenceException №" + i);
            }
        }
    }
}
