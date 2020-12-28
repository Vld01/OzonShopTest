package ru.appline.framework.pages;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.appline.framework.utils.Product;

import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BasketPage extends BasePage {
    @FindBy(xpath = "//span[contains(text(), 'Удалить')]/../../a/span")
    List<WebElement> productNameElements;

    @FindBy(xpath = "//section/div/div/span[@class][position() = 1]")  //необходим рефакторинг
    WebElement countProductsBasketElement;

    @FindBy(xpath = "//section/div/div/span[@class][position() = 2]")  //необходим рефакторинг
    WebElement countProductsElement;

    @FindBy(xpath = "//span[contains (text(), 'Удалить выбранные')]")
    WebElement deleteAllProductsElement;

    @FindBy(xpath = "//*[contains(text(), 'Удаление товаров')]")
    List<WebElement> subWindowDeleteElement;

    @FindBy(xpath = "//*[contains(text(), 'Удаление товаров')]/..//div[text() = 'Удалить']/../..")
    WebElement subWindowDeleteButton;



    @FindBy(xpath = "//*[contains(text(), 'Корзина пуста')]")
    List<WebElement> basketIsEmptyElement;


    /**
     * Функция проверки наличия товаров в корзине
     *
     * @return BasketPage - т.е. остаемся на этой странице
     */
     @Step("Проверяем наличие '{countProduct}' товаров в корзине")
     public BasketPage checkProductsInBasket(int countProduct) {
         explicitWait(2500);
         assertEquals("Количество товаров не соответствует", listProducts.size(), productNameElements.size());
         List<String> actualProductsName = new ArrayList<>();
         for (WebElement element: productNameElements) {
             actualProductsName.add(element.getText());
         }
         assertTrue("Не все товары корректно были добавлены в корзину", isContainsAllProducts(listProducts, actualProductsName));
         StringBuilder textCountProducts = new StringBuilder();
         textCountProducts.append(countProductsBasketElement.getText().trim())
                 .append(" - ")
                 .append(countProductsElement.getText().trim().split(" •")[0]);
         assertEquals("Текст 'Ваша корзина - " + countProduct + " товаров' отображается некорректно",
                 "Ваша корзина - " + countProduct + " товаров",
                 textCountProducts.toString());
         return this;
     }

    /**
     * Функция добавления в отчет Аллюра текстового файла
     *
     * @return BasketPage - т.е. остаемся на этой странице
     */
    @Step("Функция добавления в отчет Аллюра текстового файла")
    public BasketPage addTextFile() {
        Product product = listProducts.stream()
                .max(Product::compareTo)
                .get();
        try(FileWriter out = new FileWriter("src/main/resources/allProducts.txt", false)){
            out.write("Продукт с максимальной ценой: \n" + product.getName() + ", его цена: " + product.getPrice() +  " ₽\n\n");
            out.write("Список товаров: \n");
            int i = 1;
            for (Product prod : listProducts) {
                out.write("" + i + ". " +
                        prod.getName() + ", цена: " +
                        prod.getPrice() + " ₽\n");
                i++;
            }
             Allure.addAttachment("Файл со списком товаров:", "text/html", new ByteArrayInputStream(Files.readAllBytes(Paths.get("src/main/resources/allProducts.txt"))), "text");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

     /**
      * Функция удаления всех товаров из корзины
      *
      * @return BasketPage - т.е. остаемся на этой странице
      */
     @Step("Удаляем все товары из корзины")
     public BasketPage deleteAllProducts() {
         elementToBeClickable(deleteAllProductsElement).click();
         assertFalse(subWindowDeleteElement.isEmpty());
         elementToBeClickable(subWindowDeleteButton).click();
         assertFalse("Нет сообщения о том что корзина пуста", basketIsEmptyElement.isEmpty());
         return this;
     }
}
