package ru.appline.framework.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


public class SearchPage extends BasePage {
    @FindBy(xpath = "//a[@class='ui-link']")
    List<WebElement> searchProductsList;

    /**
     * Функция поиска продукта по полному имени в списке товаров
     *
     * @param fullNameProduct - наименование продукта
     * @return ProductPage - т.е. переходим на страницу {@link ru.appline.framework.pages.ProductPage}
     */
    public ProductPage clickProductSearch(String fullNameProduct){
        for (WebElement product:
             searchProductsList) {
            if(StringUtils.containsIgnoreCase(product.getText(), fullNameProduct)) {
                elementToBeClickable(product).click();
                break;
            }
        }
        return app.getProductPage();
    }
}
