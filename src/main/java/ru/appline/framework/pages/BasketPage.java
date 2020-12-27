package ru.appline.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BasketPage extends BasePage {
    @FindBy(xpath = "//span[contains(@class, 'radio-button__icon_checked')]")
    WebElement iconChecked;

    @FindBy(xpath = "//span[@class = 'price__current']")
    List<WebElement> prices;

    @FindBy(xpath = "//a[text() = 'Игра Detroit: Стать человеком (PS4)']/../../..//button[text() = 'Удалить']")
    WebElement deleteDetroit;

    @FindBy(xpath = "//a[contains(text(), 'Detroit')]")
    List<WebElement> detroit;

    @FindBy(xpath = "//i[@class = 'count-buttons__icon-plus']")
    List<WebElement> plusPS4;

    @FindBy(xpath = "//input[@class = 'count-buttons__input']")
    WebElement quantityProduct;

    @FindBy(xpath = "//span[@class = 'restore-last-removed']")
    List<WebElement> removeDeleteProduct;

    @FindBy(xpath = "//a[contains(text(), 'Detroit')]")
    List<WebElement> detroitReturn;

    @FindBy(xpath = "//div[text() = 'Итого: 4 товара']/..//span[@class = 'price__current']")
    WebElement priceFourProduct;

    @FindBy(xpath = "//span[@class = 'price__current']")
    List<WebElement> prices2;


    /**
     * Функция проверки гарантии
     *
     * @param yearWarranty - количество лет гарантии один или два
     * @return BasketPage - т.е. остаемся на этой странице
     */
     public BasketPage checkYearWarranty(int yearWarranty) {
         String textIcon = elementToBeVisible(iconChecked).getText().trim();
         switch (yearWarranty) {
             case 1:
                 Assertions.assertEquals(textIcon, "+ 12 мес.", "Не выбрана гарантия на 1 года");
                 break;
             case 2:
                 Assertions.assertEquals(textIcon, "+ 24 мес.", "Не выбрана гарантия на 2 года");
                 break;
             default:
                 Assertions.assertEquals(textIcon, "Без доп. гарантии", "Не выбрана гарантия \"Без доп. гарантии\"");
         }
         return this;
     }

     /**
      * Функция проверки цены каждого из товаров и суммы
      *
      * @return BasketPage - т.е. остаемся на этой странице
      */
     public BasketPage checkAllPrice() {
         int pricePS4 = mapProduct.get("playstation").getPrice();
         int priceDetroit = mapProduct.get("Detroit").getPrice();
         int priceBasket = pricePS4 + priceDetroit;

         int expectedPricePS4 = Integer.parseInt(prices.get(0).getText().replaceAll(" ", "").replaceAll("₽", ""));
         int expectedPriceDetroit = Integer.parseInt(prices.get(1).getText().replaceAll(" ", "").replaceAll("₽", ""));
         int expectedPriceBasket = Integer.parseInt(prices.get(2).getText().replaceAll(" ", "").replaceAll("₽", ""));

         Assertions.assertEquals(expectedPricePS4, pricePS4, "Цена PS в корзине не соответствует");
         Assertions.assertEquals(expectedPriceDetroit, priceDetroit, "Цена Detroit в корзине не соответствует");
         Assertions.assertEquals(expectedPriceBasket, priceBasket, "Сума цен PS и Detroit в корзине не соответствует");
         return this;
     }

    /**
     * Функция удаления Detroit из корзины и проверка
     * что Detroit нет больше в корзине и что сумма уменьшилась на цену Detroit
     *
     * @return BasketPage - т.е. остаемся на этой странице
     */
    public BasketPage deleteDetroit() {
        int pricePS4 = mapProduct.get("playstation").getPrice();
        int priceDetroit = mapProduct.get("Detroit").getPrice();
        elementToBeClickable(deleteDetroit).click();
        int priceBasketActual = Integer.parseInt(prices2.get(prices2.size() - 1).getText().replaceAll(" ", "").replaceAll("₽", ""));
        for (int i = 0; i < 10 && priceBasketActual != pricePS4; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            priceBasketActual = Integer.parseInt(prices2.get(prices2.size() - 1).getText().replaceAll(" ", "").replaceAll("₽", ""));
        }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(detroit.isEmpty(), "Игра Detroit в корзине присутствует");
        Assertions.assertEquals(priceBasketActual, pricePS4, "Цена корзины без Detroit не соответствует");
        return this;
    }

    /**
     * Функция добавления двух PS
     *
     * @return BasketPage - т.е. остаемся на этой странице
     */
    public BasketPage addTwoPS4() {
        plusPS4.get(0).click();
        int count = Integer.parseInt(quantityProduct.getAttribute("value"));
        for (int i = 0; i < 10 && count != 2; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count = Integer.parseInt(quantityProduct.getAttribute("value"));
        }
        plusPS4.get(0).click();
        for (int i = 0; i < 10 && count != 3; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count = Integer.parseInt(quantityProduct.getAttribute("value"));
        }
        int priceThreePS4 = mapProduct.get("playstation").getPrice() * 3;
        int priceBasketActual2 = Integer.parseInt(prices.get(prices.size() - 1).getText().replaceAll(" ", "").replaceAll("₽", ""));
        Assertions.assertEquals(priceBasketActual2, priceThreePS4, "Цена корзины не соответствует цене трех PS");
        return this;
    }

    /**
     * Функция отмены удаления товара
     *
     * @return BasketPage - т.е. остаемся на этой странице
     */
    public BasketPage returnDeleteProduct() {
        removeDeleteProduct.get(removeDeleteProduct.size() - 1).click();
        for (int i = 0; i < 10 && detroitReturn.isEmpty(); i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Assertions.assertFalse(detroitReturn.isEmpty(), "Игры Detroit в корзине нет");

        int priceBasketWithFourProduct = Integer.parseInt(priceFourProduct.getText().replaceAll(" ", "").replaceAll("₽", ""));
        int priceThreePS4AndDetroit = mapProduct.get("playstation").getPrice() * 3 + mapProduct.get("Detroit").getPrice();

        Assertions.assertEquals(priceBasketWithFourProduct, priceThreePS4AndDetroit, "Цена корзины с Detroit и тремя PS4 не соответствует");
        return this;
    }
}
